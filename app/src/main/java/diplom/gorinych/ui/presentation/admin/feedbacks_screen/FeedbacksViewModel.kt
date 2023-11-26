package diplom.gorinych.ui.presentation.admin.feedbacks_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.ui.presentation.admin.feedbacks_screen.FeedbackScreenEvent.OnChangeStatusBlockFeedback
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FeedbacksViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(FeedbacksScreenState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            _state.value.copy(
                idUser = userId
            )
                .updateStateUI()
            loadData()
        }
    }

    fun onEvent(feedbackScreenEvent: FeedbackScreenEvent) {
        when (feedbackScreenEvent) {
            is OnChangeStatusBlockFeedback -> {
                viewModelScope.launch {
                    repository.updateFeedback(
                        feedbackScreenEvent.feedback.copy(
                            isBlocked = !feedbackScreenEvent.feedback.isBlocked
                        )
                    )
                    loadData()
                }
            }
        }
    }

    private suspend fun loadData() {
        when (val result = repository.getAllFeedbacks()) {
            is Resource.Error -> {
                _state.value.copy(
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    feedbacks = result.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    private fun FeedbacksScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}