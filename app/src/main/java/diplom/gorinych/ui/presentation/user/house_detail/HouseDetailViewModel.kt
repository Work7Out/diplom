package diplom.gorinych.ui.presentation.user.house_detail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.RemoteRepository
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
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HouseDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
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
    @RequiresApi(Build.VERSION_CODES.O)
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
                        _state.value.copy(
                            promo = null,
                            additionsSelected = emptyList(),
                            message = ""
                        )
                            .updateStateUI()
                        remoteRepository.addNewHistory(
                            idUser = _state.value.idUser,
                            idHouse = _state.value.house?.id ?: -1,
                            confirmReservation = WAITING_CONFIRM,
                            dateCreate = LocalDate.now().formatLocalDateRu(),
                            dataBegin = houseDetailEvent.dateBegin.formatLocalDateRu(),
                            dataEnd = houseDetailEvent.dateEnd.formatLocalDateRu(),
                            amount = houseDetailEvent.amount,
                            additions = _state.value.additionsSelected.joinToString(separator = ", ") { it.title }
                        )
                        loadReserves()
                    }
                    remoteRepository.updatePromo(
                        id = _state.value.promo?.id ?: -1,
                        description = _state.value.promo?.description ?: "",
                        valueDiscount = _state.value.promo?.valueDiscount ?: 0,
                        isActive = false
                    )
                    delay(500)
                    _state.value.copy(
                        message = ""
                    )
                        .updateStateUI()
                }
            }

            is AddFeedback -> {
                _state.value.copy(
                    isLoading = true
                )
                    .updateStateUI()
                viewModelScope.launch {
                    remoteRepository.addNewFeedback(
                        idUser = _state.value.idUser,
                        idHouse = _state.value.house?.id ?: -1,
                        name = _state.value.nameUser,
                        dateFeedback = LocalDate.now().formatLocalDateRu(),
                        isBlocked = false,
                        rang = houseDetailEvent.rang,
                        content = houseDetailEvent.content
                    )
                    loadFeedbacks()
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
                    when (val resultLoadPromo =
                        remoteRepository.getPromoByDescription(houseDetailEvent.query)) {
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
        when (val resultUser = remoteRepository.getUserBiId(_state.value.idUser)) {
            is Resource.Error -> {
                Log.d("text detail viewmodel", "user error ${resultUser.message}")
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
        when (val resultHouses = remoteRepository.getHouseById(_state.value.idHouse)) {
            is Resource.Error -> {
                Log.d("text detail viewmodel", "house error ${resultHouses.message}")
                _state.value.copy(
                    isLoading = false,
                    message = resultHouses.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    isLoading = false,
                    message = "",
                    house = resultHouses.data
                )
                    .updateStateUI()
            }
        }
    }


    private suspend fun loadReserves() {
        when (val resultReserves = remoteRepository.getHistoryByHouse(_state.value.idHouse)) {
            is Resource.Error -> {
                Log.d("text detail viewmodel", "reserves error ${resultReserves.message}")
                _state.value.copy(
                    message = resultReserves.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                val reservesUser = resultReserves.data?.filter { it.idUser==_state.value.idUser }
                if (reservesUser.isNullOrEmpty()||reservesUser.all { convertStringToDate(it.dateBegin)< LocalDate.now() }) {
                    _state.value.copy(
                        isEnableFeedback = false,
                        message = "",
                        reserves = resultReserves.data ?: emptyList()
                    )
                        .updateStateUI()
                } else {
                    _state.value.copy(
                        isEnableFeedback = true,
                        message = "",
                        reserves = resultReserves.data
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private suspend fun loadFeedbacks() {
        when (val resultFeedbacks = remoteRepository.getFeedbacksByHouse(_state.value.idHouse)) {

            is Resource.Error -> {
                Log.d("text detail viewmodel", "feedback error ${resultFeedbacks.message}")
                _state.value.copy(
                    isLoading = false,
                    message = resultFeedbacks.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    isLoading = false,
                    message = "",
                    feedbacks = resultFeedbacks.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadAdditions() {
        when (val resultFeedbacks = remoteRepository.getAllAddons()) {
            is Resource.Error -> {
                Log.d("text detail viewmodel", "addons error ${resultFeedbacks.message}")
                _state.value.copy(
                    message = resultFeedbacks.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    additions = resultFeedbacks.data ?: emptyList()
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