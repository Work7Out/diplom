package diplom.gorinych.ui.presentation.user.house_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.model.Promo
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.DATA_LESS_TODAY
import diplom.gorinych.domain.utils.EXPIRED_PROMO
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.SOME_DATES_UNAVAIBLE
import diplom.gorinych.domain.utils.SUCCESS_PROMO
import diplom.gorinych.domain.utils.UNCORRECT_PROMO
import diplom.gorinych.domain.utils.WAITING_CONFIRM
import diplom.gorinych.domain.utils.convertStringToDate
import diplom.gorinych.domain.utils.formatLocalDateRu
import diplom.gorinych.ui.presentation.user.house_detail.HouseDetailEvent.AddFeedback
import diplom.gorinych.ui.presentation.user.house_detail.HouseDetailEvent.AddReserve
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            val houseId = savedStateHandle.get<Int>("idHouse") ?: return@launch
            _state.value.copy(
                idUser = userId,
                idHouse = houseId
            )
                .updateStateUI()
            async { loadUserData() }.onAwait
            async { loadHouseData() }.onAwait
            async { loadFeedbacks() }.onAwait
            async { loadReserves() }.onAwait
            async { loadAdditions() }.onAwait
        }
    }

    fun onEvent(houseDetailEvent: HouseDetailEvent) {
        when (houseDetailEvent) {
            is AddReserve -> {
                viewModelScope.launch {
                    if (houseDetailEvent.dateBegin < LocalDate.now() || houseDetailEvent.dateEnd < LocalDate.now()) {
                        _state.value.copy(
                            message = DATA_LESS_TODAY
                        )
                            .updateStateUI()
                    } else if (_state.value.reserves.any {
                            (convertStringToDate(it.dateBegin) <= houseDetailEvent.dateEnd
                                    && convertStringToDate(it.dateEnd) >= houseDetailEvent.dateBegin)
                        }) {
                        _state.value.copy(
                            message = SOME_DATES_UNAVAIBLE
                        )
                            .updateStateUI()
                    } else {
                        val sumAddons = _state.value.additionsSelected.sumOf { it.price }
                        val price = _state.value.house?.price ?: 0.0
                        _state.value.copy(
                            additionsSelected = emptyList()
                        )
                            .updateStateUI()
                        repository.addReserve(
                            idUser = _state.value.idUser,
                            idHouse = _state.value.house?.id ?: -1,
                            confirmReservation = WAITING_CONFIRM,
                            dateCreate = LocalDate.now().formatLocalDateRu(),
                            dateBegin = houseDetailEvent.dateBegin.formatLocalDateRu(),
                            dateEnd = houseDetailEvent.dateEnd.formatLocalDateRu(),
                            amount = (houseDetailEvent.valueDays * price + sumAddons) * (1 - (_state.value.promo?.valueDiscount
                                ?: 0).toDouble() / 100),
                            addtions = _state.value.additionsSelected.joinToString(separator = ", ") { it.title }
                        )
                    }
                    repository.updatePromo(
                        Promo(
                            id = _state.value.promo?.id ?: -1,
                            description = _state.value.promo?.description ?: "",
                            valueDiscount = _state.value.promo?.valueDiscount ?: 0,
                            isActive = false
                        )
                    )
                    delay(500)
                    _state.value.copy(
                        promo = null,
                        message = ""
                    )
                        .updateStateUI()
                }
            }

            is AddFeedback -> {
                viewModelScope.launch {
                    repository.insertFeedback(
                        idUser = _state.value.idUser,
                        idHouse = _state.value.house?.id ?: -1,
                        dateCreate = LocalDate.now().formatLocalDateRu(),
                        isBlocked = false,
                        rang = houseDetailEvent.rang,
                        content = houseDetailEvent.content
                    )
                }
            }

            is HouseDetailEvent.AddAddon -> {
                val mutableSelectedAddons = _state.value.additionsSelected.toMutableList()
                if (mutableSelectedAddons.contains(houseDetailEvent.addon)) {
                    val updatedList = mutableSelectedAddons.filter { it != houseDetailEvent.addon }
                    _state.value.copy(
                        additionsSelected = updatedList
                    )
                        .updateStateUI()
                } else {
                    mutableSelectedAddons.add(houseDetailEvent.addon)
                    _state.value.copy(
                        additionsSelected = mutableSelectedAddons
                    )
                        .updateStateUI()
                }
            }

            is HouseDetailEvent.CheckPromo -> {
                viewModelScope.launch {
                    when (val resultLoadPromo = repository.getPromoByName(houseDetailEvent.query)) {
                        is Resource.Error -> {
                            _state.value.copy(
                                message = resultLoadPromo.message
                            )
                                .updateStateUI()
                        }

                        is Resource.Success -> {
                            if (resultLoadPromo.data == null) {
                                _state.value.copy(
                                    message = UNCORRECT_PROMO
                                )
                                    .updateStateUI()
                            } else {
                                if (resultLoadPromo.data.isActive) {
                                    _state.value.copy(
                                        promo = resultLoadPromo.data,
                                        message = "$SUCCESS_PROMO ${resultLoadPromo.data.valueDiscount} %"
                                    )
                                        .updateStateUI()
                                } else {
                                    _state.value.copy(
                                        message = EXPIRED_PROMO
                                    )
                                        .updateStateUI()
                                }
                            }
                        }

                        null -> {
                            _state.value.copy(
                                message = UNCORRECT_PROMO
                            )
                                .updateStateUI()
                        }
                    }
                    delay(500)
                    _state.value.copy(
                        message = ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private suspend fun loadUserData() {
        when (val resultUser = repository.getUserById(_state.value.idUser)) {
            is Resource.Error -> {
                _state.value.copy(
                    message = resultUser.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    message = "",
                    idUser = resultUser.data?.id ?: -1,
                    nameUser = resultUser.data?.name ?: ""
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadHouseData() {
        when (val resultHouses = repository.getDetailHouse(_state.value.idHouse)) {
            is Resource.Error -> {
                _state.value.copy(
                    message = resultHouses.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    message = "",
                    house = resultHouses.data
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadReserves() {
        val resultReserves = repository.getHistoryByIdHouse(_state.value.idHouse)
        resultReserves.collect {
            when (it) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = it.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        message = "",
                        reserves = it.data ?: emptyList()
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private suspend fun loadFeedbacks() {
        val resultFeedbacks = repository.getFeedbackByHouse(_state.value.idHouse)
        resultFeedbacks.collect {
            when (it) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = it.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        message = "",
                        feedbacks = it.data ?: emptyList()
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private suspend fun loadAdditions() {
        val resultFeedbacks = repository.getAddons()
        resultFeedbacks.collect {
            when (it) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = it.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        additions = it.data ?: emptyList()
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun HouseDetailScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}