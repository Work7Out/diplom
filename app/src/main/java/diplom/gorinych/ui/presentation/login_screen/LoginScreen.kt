package diplom.gorinych.ui.presentation.login_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center)
        ) {
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = state.value.login,
                onValueChange = {
                    onEvent(LoginEvent.SetLogin(it))
                })
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = state.value.password,
                onValueChange = {
                    onEvent(LoginEvent.SetPassword(it))
                })
            Button(onClick = {
                onEvent(LoginEvent.OnLogin)
                scope.launch {
                    delay(1000)
                    if (state.value.idUser!=-1) {
                        if (state.value.role=="admin") {
                            navController.navigate("usersScreen/${state.value.idUser}")
                        } else {
                            navController.navigate("listHousesUserScreen/${state.value.idUser}")
                        }
                    }
                }
            }) {
                Text(text = "login")
            }
            if (state.value.message != null) {
                Text(text = state.value.message!!)
            }
        }
    }
}