package diplom.gorinych.ui.presentation.registration_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.R.font
import diplom.gorinych.R.string
import diplom.gorinych.domain.utils.ALREADY_EXIST
import diplom.gorinych.domain.utils.PhoneNumberTransformation
import diplom.gorinych.domain.utils.SUCCESS_REGISTRATION
import diplom.gorinych.domain.utils.isEmailValid
import diplom.gorinych.domain.utils.sendMail
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.thirdText
import diplom.gorinych.ui.theme.white
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

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
            .background(color = white)
            .padding(10.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center)
        ) {
            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.registration_upper),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(font.gilroy)),
                    fontWeight = FontWeight(700),
                    color = baseText,
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(26.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = name.value,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.enter_login),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(500),
                            color = secondText,
                        )
                    )
                },
                onValueChange = {
                    name.value = it
                }
            )
            Spacer(modifier = modifier.height(10.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = password.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = stringResource(id = string.enter_password),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(500),
                            color = secondText,
                        )
                    )
                },
                onValueChange = {
                    password.value = it
                }
            )
            Spacer(modifier = modifier.height(10.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = phone.value,
                placeholder = {
                    Text(
                        text = stringResource(id = string.enter_phone),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(500),
                            color = secondText,
                        )
                    )
                },
                onValueChange = {
                    phone.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                visualTransformation = PhoneNumberTransformation()
            )
            Spacer(modifier = modifier.height(10.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = email.value,
                placeholder = {
                    Text(
                        text = stringResource(id = string.enter_email),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(500),
                            color = secondText,
                        )
                    )
                },
                onValueChange = {
                    email.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            Spacer(modifier = modifier.height(26.dp))
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = {
                    onEvent(
                        RegistrationEvent.OnRegistrationUser(
                            name = name.value,
                            password = password.value,
                            phone = phone.value,
                            email = email.value
                        )

                    )
                    scope.launch (Dispatchers.IO) {
                        sendMail(
                            login = "edurda77@gmail.com",
                            password = "Khayarov1977!",
                            email = email.value,
                            theme = SUCCESS_REGISTRATION,
                            content = "${name.value}\n${password.value}\n${phone.value}\n${email.value}"
                        )
                    }
                    scope.launch {
                        delay(1000)
                        if (state.value.message != ALREADY_EXIST) {
                            navController.navigate("loginScreen")
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue
                ),
                enabled = name.value.isNotEmpty()
                        && password.value.isNotEmpty()
                        && phone.value.isNotEmpty()
                        && email.value.isNotEmpty()
                        && isEmailValid(email.value)
            ) {
                Text(
                    text = stringResource(id = string.do_registration),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(700),
                        color = white,
                    )
                )
            }
            if (state.value.message != null) {
                Text(
                    text = state.value.message!!,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = thirdText,
                    )
                )
            }
        }
        TextButton(
            modifier = modifier
                .align(alignment = Alignment.BottomCenter),
            onClick = {
                navController.navigate("loginScreen")
            }) {
            Text(
                text = stringResource(id = string.already_login),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(font.gilroy)),
                    fontWeight = FontWeight(500),
                    color = secondText,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}