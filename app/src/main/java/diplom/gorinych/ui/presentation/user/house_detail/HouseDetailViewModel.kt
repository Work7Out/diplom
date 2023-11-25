package diplom.gorinych.ui.presentation.user.house_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.WAITING_CONFIRM
import diplom.gorinych.domain.utils.formatLocalDateRu
import diplom.gorinych.ui.presentation.user.house_detail.HouseDetailEvent.AddFeedback
import diplom.gorinych.ui.presentation.user.house_detail.HouseDetailEvent.AddReserve
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HouseDetailViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(HouseDetailScreenState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(houseDetailEvent: HouseDetailEvent) {
        when (houseDetailEvent) {
            is AddReserve -> {
                val price = _state.value.house?.price?:0.0
                viewModelScope.launch {
                    repository.addReserve(
                        idUser = _state.value.idUser,
                        idHouse = _state.value.house?.id?:-1,
                        confirmReservation = WAITING_CONFIRM,
                        dateCreate = LocalDate.now().formatLocalDateRu(),
                        dateBegin = houseDetailEvent.dateBegin.formatLocalDateRu(),
                        dateEnd = houseDetailEvent.dateEnd.formatLocalDateRu(),
                        amount = houseDetailEvent.valueDays*price
                    )
                }
            }

            is AddFeedback -> {
                viewModelScope.launch {
                    repository.insertFeedback(
                        idUser = _state.value.idUser,
                        idHouse = _state.value.house?.id?:-1,
                        dateCreate = LocalDate.now().formatLocalDateRu(),
                        isBlocked = false,
                        rang = houseDetailEvent.rang,
                        content = houseDetailEvent.content
                    )
                    getHouse(_state.value.house?.id?:-1)
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            val houseId = savedStateHandle.get<Int>("idHouse") ?: return@launch
            _state.value.copy(
                idUser = userId,
                idHouse = houseId
            )
                .updateStateUI()
            when (val resultUser = repository.getUserById(userId)) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = resultUser.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        nameUser = resultUser.data?.name ?: ""
                    )
                        .updateStateUI()
                }
            }
            getHouse(houseId)
        }
    }

    private suspend fun getHouse(id:Int) {
        when (val resultHouses = repository.getDetailHouse(id)) {
            is Resource.Error -> {
                _state.value.copy(
                    message = resultHouses.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    house = resultHouses.data
                )
                    .updateStateUI()
            }
        }
    }
    private fun HouseDetailScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}