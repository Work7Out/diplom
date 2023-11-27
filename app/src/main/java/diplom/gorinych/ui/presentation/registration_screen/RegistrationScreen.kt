package diplom.gorinych.ui.presentation.registration_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.domain.utils.ALREADY_EXIST
import diplom.gorinych.domain.utils.isEmailValid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
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
                value = name.value,
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_login))
                },
                onValueChange = {
                    name.value = it
                }
            )
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = password.value,
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_password))
                },
                onValueChange = {
                    password.value = it
                }
            )
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = phone.value,
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_phone))
                },
                onValueChange = {
                    phone.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = email.value,
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_email))
                },
                onValueChange = {
                    email.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            Button(
                onClick = {
                    onEvent(
                        RegistrationEvent.OnRegistrationUser(
                            name = name.value,
                            password = password.value,
                            phone = phone.value,
                            email = email.value
                        )

                    )
                    scope.launch {
                        delay(1000)
                        if (state.value.message!=ALREADY_EXIST) {
                            navController.navigate("loginScreen")
                        }
                    }
                },
                enabled = name.value.isNotEmpty()
                        && password.value.isNotEmpty()
                        && phone.value.isNotEmpty()
                        && email.value.isNotEmpty()
                        && isEmailValid(email.value)
            ) {
                Text(text = stringResource(id = R.string.do_registration))
            }
            if (state.value.message != null) {
                Text(text = state.value.message!!)
            }
        }
        Text(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("loginScreen")
                }
                .align(alignment = Alignment.BottomCenter),
            text = stringResource(id = R.string.already_login),
            textAlign = TextAlign.Center)
    }
}