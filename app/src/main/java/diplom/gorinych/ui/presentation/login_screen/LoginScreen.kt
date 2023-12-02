package diplom.gorinych.ui.presentation.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import diplom.gorinych.ui.presentation.login_screen.LoginScreenWindowState.BaseScreen
import diplom.gorinych.ui.presentation.login_screen.LoginScreenWindowState.SplashScreen
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.thirdText
import diplom.gorinych.ui.theme.white
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

    when (state.value.loginScreenWindowState) {
        SplashScreen -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = blue)
                    .padding(10.dp)
            ) {
                Image(
                    modifier = modifier
                        .size(200.dp)
                        .align(alignment = Alignment.Center),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = ""
                )
            }
        }

        BaseScreen -> {
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
                        text = stringResource(id = R.string.authorization),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(700),
                            color = baseText,
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = modifier.height(26.dp))
                    TextField(
                        modifier = modifier
                            .fillMaxWidth(),
                        value = state.value.login,
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.login),
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.gilroy)),
                                    fontWeight = FontWeight(500),
                                    color = secondText,
                                )
                            )
                        },
                        onValueChange = {
                            onEvent(LoginEvent.SetLogin(it))
                        })
                    Spacer(modifier = modifier.height(22.dp))
                    TextField(
                        modifier = modifier
                            .fillMaxWidth(),
                        value = state.value.password,
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.password),
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.gilroy)),
                                    fontWeight = FontWeight(500),
                                    color = secondText,
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = {
                            onEvent(LoginEvent.SetPassword(it))
                        })
                    Spacer(modifier = modifier.height(26.dp))
                    Button(
                        modifier = modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = blue
                        ),
                        onClick = {
                            onEvent(LoginEvent.OnLogin)
                            scope.launch {
                                delay(1000)
                                if (state.value.idUser != -1) {
                                    if (state.value.role == "admin") {
                                        navController.navigate("usersScreen/${state.value.idUser}")
                                    } else {
                                        navController.navigate("listHousesUserScreen/${state.value.idUser}")
                                    }
                                }
                            }
                        }) {
                        Text(
                            text = stringResource(id = R.string.enter),
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
                        navController.navigate("registrationScreen")
                    }) {
                    Text(
                        text = stringResource(id = R.string.registration),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(500),
                            color = secondText,
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}