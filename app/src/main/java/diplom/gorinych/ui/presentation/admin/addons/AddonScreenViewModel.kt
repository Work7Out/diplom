package diplom.gorinych.ui.presentation.admin.addons

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.model.Addon
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.repository.SharedRepository
import diplom.gorinych.domain.utils.GET_IS_AWAITED
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.WAITING_CONFIRM
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddonScreenViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedRepository: SharedRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddonScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            _state.value.copy(
                idUser = userId
            )
                .updateStateUI()
            async { loadPromos() }.onAwait
            async { loadAddons() }.onAwait
            async { loadNewReserves() }.onAwait
        }
    }

    fun onEvent(event: AddonScreenEvent) {
        when (event) {
            is AddonScreenEvent.InsertAddon -> {
                _state.value.copy(
                    isLoading = true,
                )
                    .updateStateUI()
                viewModelScope.launch {
                    remoteRepository.addNewAddon(
                        title = event.title,
                        price = event.price
                    )
                    loadAddons()
                }
            }

            is AddonScreenEvent.InsertPromo -> {
                _state.value.copy(
                    isLoading = true,
                )
                    .updateStateUI()
                viewModelScope.launch {
                    remoteRepository.addNewPromo(
                        valueDiscount = event.value,
                        description = event.description,
                        isActive = true
                    )
                    loadPromos()
                }
            }

            is AddonScreenEvent.ChangeState -> {
                _state.value.copy(
                    addonState = event.addonState
                )
                    .updateStateUI()
            }

            AddonScreenEvent.Exit -> {
                sharedRepository.setUser(-1)
                sharedRepository.setRole("")
            }

            is AddonScreenEvent.DeleteAddon -> {
                viewModelScope.launch {
                    remoteRepository.deleteAddon(
                        addonId = event.addon.id
                    )
                    loadAddons()
                }
            }

            is AddonScreenEvent.UpdateAddon -> {
                viewModelScope.launch {
                    remoteRepository.updateAddon(
                        addon = Addon(
                            id = event.id,
                            title = event.title,
                            price = event.price
                        )
                    )
                    loadAddons()
                }
            }
        }
    }

    private suspend fun loadNewReserves() {
        when (val result = remoteRepository.getHistoryByStatus(status = GET_IS_AWAITED)) {
            is Resource.Error -> {
                _state.value.copy(
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    countNewReserves = result.data?.size ?: 0
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadAddons() {
        when (val result = remoteRepository.getAllAddons()) {
            is Resource.Error -> {
                _state.value.copy(
                    isLoading = false,
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                Log.d("TAG result addons", "result addons ${result.data}")
                _state.value.copy(
                    isLoading = false,
                    addons = result.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadPromos() {
        when (val result = remoteRepository.getAllPromos()) {
            is Resource.Error -> {
                _state.value.copy(
                    isLoading = false,
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    isLoading = false,
                    promos = result.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    private fun AddonScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}