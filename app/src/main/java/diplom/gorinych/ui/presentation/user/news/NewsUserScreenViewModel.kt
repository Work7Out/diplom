package diplom.gorinych.ui.presentation.user.news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.repository.SharedRepository
import diplom.gorinych.domain.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsUserScreenViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedRepository: SharedRepository
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
                    remoteRepository.addNewCall(
                        name = _state.value.user?.name ?: "",
                        phone = _state.value.user?.phone ?: "",
                        isResponse = false
                    )
                }
            }

            NewsUserScreenEvent.Exit -> {
                sharedRepository.setUser(-1)
                sharedRepository.setRole("")
            }
        }
    }

    private suspend fun loadUserData(userId: Int) {
        when (val resultUser = remoteRepository.getUserBiId(userId)) {
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


    private fun NewsUserScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}