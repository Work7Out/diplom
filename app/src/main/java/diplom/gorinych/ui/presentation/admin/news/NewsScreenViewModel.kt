package diplom.gorinych.ui.presentation.admin.news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.model.Note
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.formatLocalDateRu
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val repository: HouseRepository,
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
                viewModelScope.launch {
                    repository.addNote(
                        title = event.title,
                        content = event.content,
                        dateCreate = LocalDate.now().formatLocalDateRu()
                    )
                }
            }

            is NewsScreenEvent.DeleteNote -> {
                viewModelScope.launch {
                    repository.deleteNote(
                        event.note
                    )
                }
            }

            is NewsScreenEvent.UpdateNote -> {
                viewModelScope.launch {
                    repository.updateNote(
                        Note(
                            id = event.id,
                            title = event.title,
                            content = event.content,
                            dateCreate = LocalDate.now().formatLocalDateRu()
                        )
                    )
                }
            }
        }
    }

    private suspend fun loadNewsData() {
        val result = repository.getNews()
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
                        news = it.data ?: emptyList()
                    )
                        .updateStateUI()
                }
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

    private fun NewsScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}