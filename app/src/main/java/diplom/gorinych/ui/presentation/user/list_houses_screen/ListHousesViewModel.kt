package diplom.gorinych.ui.presentation.user.list_houses_screen

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
class ListHousesViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle
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
                    repository.addCall(
                        name = _state.value.user?.name ?: "",
                        phone = _state.value.user?.phone ?: ""
                    )
                }
            }
        }
    }

    private suspend fun loadUserData(userId: Int) {
        when (val resultUser = repository.getUserById(userId)) {
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
        val resultHouses = repository.getAllHouses()
        resultHouses.collect {houses->
            when (houses) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = houses.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        houses = houses.data ?: emptyList()
                    )
                        .updateStateUI()
                }
            }
        }
    }


    private fun ListHousesScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}