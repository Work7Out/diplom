package diplom.gorinych.ui.presentation.admin.history_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.utils.BLOCKED
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.USER
import diplom.gorinych.domain.utils.USER_BLOCKED
import diplom.gorinych.ui.presentation.admin.history_screen.HistoryScreenEvent.OnConfirmReserve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HouseRepository,
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
                viewModelScope.launch {
                    repository.updateHistory(
                        historyScreenEvent.reserve.copy(
                            confirmReservation = historyScreenEvent.status
                        )
                    )
                }
            }

            is HistoryScreenEvent.OnChangeStatusBlockFeedback -> {
                viewModelScope.launch {
                    repository.updateFeedback(
                        historyScreenEvent.feedback.copy(
                            isBlocked = !historyScreenEvent.feedback.isBlocked
                        )
                    )
                    when (val load = repository.getUserById(historyScreenEvent.feedback.idUser)) {
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
                }
                viewModelScope.launch(Dispatchers.IO) {
                    delay(3000)
                    if (_user.value != null) {
                        mailRepository.sendEmail(
                            login = "edurda77@gmail.com",
                            password = "Khayarov1977!",
                            email = _user.value!!.email,
                            theme = historyScreenEvent.message,
                            content = "$USER ${_user.value!!.name} $BLOCKED"
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

    private suspend fun loadReserveData() {
        val result = repository.getAllHistory()
        result.collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = resource.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        reserves = resource.data ?: emptyList()
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private suspend fun loadFeedbackData() {
        val result = repository.getAllFeedbacks()
        result.collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = resource.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        feedbacks = resource.data ?: emptyList()
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun HistoryScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}