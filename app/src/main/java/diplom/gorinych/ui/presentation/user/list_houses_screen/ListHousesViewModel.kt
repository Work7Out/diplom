package diplom.gorinych.ui.presentation.user.list_houses_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.repository.SharedRepository
import diplom.gorinych.domain.utils.Resource
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ListHousesViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedRepository: SharedRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ListHousesScreenState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            async{ loadUserData(userId) } .onAwait
            async{ loadHousesData() } .onAwait
        }
    }

    fun onEvent(event: ListHousesEvent) {
        when (event) {
            ListHousesEvent.OnSendCall -> {
                viewModelScope.launch {
                    remoteRepository.addNewCall(
                        name = _state.value.user?.name ?: "",
                        phone = _state.value.user?.phone ?: ""
                    )
                }
            }

            ListHousesEvent.Exit -> {
                sharedRepository.setUser(-1)
                sharedRepository.setRole("")
            }
        }
    }

    private suspend fun loadUserData(userId: Int) {
        when (val resultUser = remoteRepository.getUserBiId(userId)) {
            is Resource.Error -> {
                _state.value.copy(
                    message = resultUser.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    user = resultUser.data
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadHousesData() {
        when (val houses = remoteRepository.getAllHouses()) {
            is Resource.Error -> {
                _state.value.copy(
                    isLoading = false,
                    message = houses.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    isLoading = false,
                    houses = houses.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }


    private fun ListHousesScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}