package diplom.gorinych.ui.presentation.role_screen

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import diplom.gorin.RoleViewModel

@Composable
fun RoleScreen (
    modifier: Modifier = Modifier,
    viewModel: RoleViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    Log.d ("TAG", "message - ${state.value.message}")
    if (state.value.message!=null) {
        Text(text = state.value.message!!)
    }
}