package diplom.gorinych.ui.presentation.admin.addons

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddonScreenViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle
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
                viewModelScope.launch {
                    repository.addAddon(
                        title = event.title,
                        price = event.price
                    )
                }
            }

            is AddonScreenEvent.InsertPromo -> {
                viewModelScope.launch {
                    repository.addPromo(
                        valueDiscount = event.value,
                        description = event.description,
                        isActive = true
                    )
                }
            }

            is AddonScreenEvent.ChangeState -> {
                _state.value.copy(
                    addonState = event.addonState
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadNewReserves() {
        val result = repository.getHistoryNoConfirmStatus()
        result.collect {
            when (it) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = it.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        countNewReserves = it.data?.size ?: 0
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private suspend fun loadAddons() {
        val result = repository.getAddons()
        result.collect {
            when (it) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = it.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    Log.d("TAG result addons","result addons ${it.data}")
                    _state.value.copy(
                        addons = it.data ?: emptyList()
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private suspend fun loadPromos() {
        val result = repository.getPromos()
        result.collect {
            when (it) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = it.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        promos = it.data ?: emptyList()
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun AddonScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}