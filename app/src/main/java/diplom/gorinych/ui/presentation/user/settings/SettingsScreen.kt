package diplom.gorinych.ui.presentation.user.settings

import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import diplom.gorinych.ui.presentation.base.AppBarUser
import diplom.gorinych.ui.presentation.base.BottomBarUser
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.grey
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.thirdText
import diplom.gorinych.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent

    val oldPassword = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val repeatPassword = remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBarUser(
                navController = navController,
                onSendCall = {
                    onEvent(SettingsEvent.OnSendCall)
                })
        },
        bottomBar = {
            BottomBarUser(
                navController = navController,
                idUser = state.value.user?.id ?: -1
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = grey)
                .padding(10.dp),
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(id = R.string.change_password),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = baseText
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(10.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = oldPassword.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.old_password),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(500),
                            color = secondText,
                        )
                    )
                },
                onValueChange = {
                    oldPassword.value = it
                }
            )
            Spacer(modifier = modifier.height(10.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = newPassword.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.new_password),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(500),
                            color = secondText,
                        )
                    )
                },
                onValueChange = {
                    newPassword.value = it
                }
            )
            Spacer(modifier = modifier.height(10.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = repeatPassword.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.repeat_password),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(500),
                            color = secondText,
                        )
                    )
                },
                onValueChange = {
                    repeatPassword.value = it
                }
            )
            Spacer(modifier = modifier.height(26.dp))
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = {
                    onEvent(
                        SettingsEvent.OnChangePassword(
                            oldPassword = oldPassword.value,
                            password = newPassword.value,
                            repeatPassword = repeatPassword.value
                        )
                    )
                    oldPassword.value = ""
                    newPassword.value = ""
                    repeatPassword.value = ""
                },
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue
                ),
                enabled = oldPassword.value.isNotEmpty()
                        && newPassword.value.isNotEmpty()
                        && repeatPassword.value.isNotEmpty()
            ) {
                Text(
                    text = stringResource(id = R.string.change_password),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(700),
                        color = white,
                    )
                )
            }
            if (!state.value.message.isNullOrBlank()) {
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
    }

}