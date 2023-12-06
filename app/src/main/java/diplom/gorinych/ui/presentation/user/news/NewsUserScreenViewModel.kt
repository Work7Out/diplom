package diplom.gorinych.ui.presentation.user.news

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
class NewsUserScreenViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(NewsUserScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch

            async { loadNewsData() }.onAwait
            async { loadUserData(userId) }.onAwait
        }
    }

    fun onEvent(event: NewsUserScreenEvent) {
        when (event) {
            NewsUserScreenEvent.OnSendCall -> {
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
        when (val result = repository.getUserById(userId)) {
            is Resource.Error -> {
                _state.value.copy(
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    user = result.data
                )
                    .updateStateUI()
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


    private fun NewsUserScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}