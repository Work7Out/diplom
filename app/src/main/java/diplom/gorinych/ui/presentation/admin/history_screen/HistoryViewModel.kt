package diplom.gorinych.ui.presentation.admin.history_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.utils.EMAIL_LOGIN
import diplom.gorinych.domain.utils.EMAIL_PASSWORD
import diplom.gorinych.domain.utils.FEEDBACK_ST
import diplom.gorinych.domain.utils.GET_IS_AWAITED
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.WAITING_CONFIRM
import diplom.gorinych.ui.presentation.admin.history_screen.HistoryScreenEvent.OnConfirmReserve
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val mailRepository: MailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(HistoryScreenState())
    val state = _state.asStateFlow()
    private val _user = MutableStateFlow<User?>(null)

    init {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            _state.value.copy(
                idUser = userId
            )
                .updateStateUI()
            async { loadReserveData() }.onAwait
            async { loadNewReserves() }.onAwait
            async { loadFeedbackData() }.onAwait
        }
    }


    fun onEvent(historyScreenEvent: HistoryScreenEvent) {
        when (historyScreenEvent) {
            is OnConfirmReserve -> {
                _state.value.copy(
                    isLoading = true,
                )
                    .updateStateUI()
                viewModelScope.launch {
                    remoteRepository.updateHistory(
                        historyScreenEvent.reserve.copy(
                            confirmReservation = historyScreenEvent.status
                        )
                    )
                    loadNewReserves()
                    loadReserveData()
                }
            }

            is HistoryScreenEvent.OnChangeStatusBlockFeedback -> {
                viewModelScope.launch {
                    remoteRepository.updateFeedback(
                        historyScreenEvent.feedback.copy(
                            isBlocked = !historyScreenEvent.feedback.isBlocked
                        )
                    )
                    when (val load = remoteRepository.getUserBiId(historyScreenEvent.feedback.idUser)) {
                        is Resource.Error -> {
                            _state.value.copy(
                                message = load.message
                            )
                                .updateStateUI()
                        }

                        is Resource.Success -> {
                            _user.value = load.data
                        }
                    }
                    loadFeedbackData()
                }
                viewModelScope.launch(Dispatchers.IO) {
                    delay(5000)
                    if (_user.value != null) {
                        mailRepository.sendEmail(
                            login = EMAIL_LOGIN,
                            password = EMAIL_PASSWORD,
                            email = _user.value!!.email,
                            theme = historyScreenEvent.message,
                            content = "$FEEDBACK_ST ${historyScreenEvent.feedback.content} ${historyScreenEvent.message}"
                        )
                    }
                }
            }

            is HistoryScreenEvent.OnChangeHistoryState -> {
                _state.value.copy(
                    historyState = historyScreenEvent.historyState
                )
                    .updateStateUI()
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

    private suspend fun loadReserveData() {
        when (val result = remoteRepository.getAllHistory()) {
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
                    reserves = result.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadFeedbackData() {
        when (val result = remoteRepository.getAllFeedbacks()) {
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
                    feedbacks = result.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    private fun HistoryScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}