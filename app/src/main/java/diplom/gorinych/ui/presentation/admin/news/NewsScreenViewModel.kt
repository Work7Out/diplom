package diplom.gorinych.ui.presentation.admin.news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.model.Note
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.WAITING_CONFIRM
import diplom.gorinych.domain.utils.formatLocalDateRu
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(NewsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            _state.value.copy(
                idUser = userId
            )
                .updateStateUI()
            async { loadNewsData() }.onAwait
            async { loadNewReserves() }.onAwait
        }
    }

    fun onEvent(event: NewsScreenEvent) {
        when (event) {
            is NewsScreenEvent.AddNote -> {
                _state.value.copy(
                    isLoading = true,
                )
                    .updateStateUI()
                viewModelScope.launch {
                    remoteRepository.addNewNews(
                        title = event.title,
                        content = event.content,
                        dateCreate = LocalDate.now().formatLocalDateRu()
                    )
                    loadNewsData()
                }
            }

            is NewsScreenEvent.DeleteNote -> {
                _state.value.copy(
                    isLoading = true,
                )
                    .updateStateUI()
                viewModelScope.launch {
                    remoteRepository.deleteNews(event.note.id)
                    loadNewsData()
                }
            }

            is NewsScreenEvent.UpdateNote -> {
                _state.value.copy(
                    isLoading = true,
                )
                    .updateStateUI()
                viewModelScope.launch {
                    remoteRepository.updateNews(
                        Note(
                            id = event.id,
                            title = event.title,
                            content = event.content,
                            dateCreate = LocalDate.now().formatLocalDateRu()
                        )
                    )
                    loadNewsData()
                }
            }
        }
    }

    private suspend fun loadNewsData() {
        when (val result = remoteRepository.getAllNews()) {
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
                    news = result.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadNewReserves() {
        when (val result = remoteRepository.getHistoryByStatus(status = WAITING_CONFIRM)) {
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

    private fun NewsScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}