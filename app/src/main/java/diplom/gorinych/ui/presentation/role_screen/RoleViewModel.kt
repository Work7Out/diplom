package diplom.gorin

import android.util.Log
import diplom.gorinych.domain.utils.ROLE_ADMIN
import diplom.gorinych.ui.presentation.role_screen.RoleScreenState

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.model.RoleState
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoleViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(RoleScreenState())
    val state = _state.asStateFlow()

    init {
        setUserByRole()
    }

    private fun setUserByRole() {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            //checkNotNull(savedStateHandle["idUser"]) as Int
            Log.d ("TAG", "userId - $userId")
            when (val result = repository.getUserById(userId)) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message
                    )
                        .updateStateUI()
                }
                is Resource.Success -> {
                    if (result.data!=null) {
                        if (result.data.role==ROLE_ADMIN) {
                            _state.value.copy(
                                message = "admin",
                                roleState = RoleState.AdminRole
                            )
                                .updateStateUI()
                        } else {
                            _state.value.copy(
                                message = "user",
                                roleState = RoleState.UserRole
                            )
                                .updateStateUI()
                        }
                    }
                }
            }
        }
    }

    private fun RoleScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}