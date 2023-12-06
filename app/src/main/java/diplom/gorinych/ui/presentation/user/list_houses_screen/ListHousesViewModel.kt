package diplom.gorinych.ui.presentation.user.list_houses_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
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
        loadData()
    }

    fun onEvent(event: ListHousesEvent) {
        when (event) {
            ListHousesEvent.onSendCall -> {
                viewModelScope.launch {
                    repository.addCall(
                        name = _state.value.user?.name ?: "",
                        phone = _state.value.user?.phone ?: ""
                    )
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
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
            when (val resultHouses = repository.getAllHouses()) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = resultHouses.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        houses = resultHouses.data ?: emptyList()
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