package diplom.gorinych.ui.presentation.admin.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diplom.gorinych.R
import diplom.gorinych.R.string
import diplom.gorinych.domain.model.User
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.white

@Composable
fun ItemUser(
    modifier: Modifier = Modifier,
    user: User,
    idUser: Int,
    onEvent: (UsersScreenEvent) -> Unit
) {
    val roles = listOf("user", "admin")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = white)
            .padding(10.dp)
    ) {
        Text(
            text = "${stringResource(id = string.user_login)} ${user.name}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = string.user_password)} ${user.password}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = string.user_role)} ${user.role}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = if (user.isBlocked) stringResource(id = string.blocked) else stringResource(id = string.not_blocked),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,
                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = string.phone)} ${user.phone}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = string.user_email)} ${user.email}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(10.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(),
            enabled = idUser != user.id,
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            ),
            onClick = {
                onEvent(UsersScreenEvent.OnChangeStatusBlock(user))
            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = if (user.isBlocked) stringResource(id = string.make_unblocked) else stringResource(id = string.make_blocked) ,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = white
                ),
                textAlign = TextAlign.Center
            )
        }
        if (idUser != user.id) {
            Spacer(modifier = modifier.height(5.dp))
            roles.forEach { role ->
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    RadioButton(
                        selected = (role == user.role),
                        colors = RadioButtonDefaults.colors(
                            selectedColor = blue,
                            unselectedColor = blue
                        ),
                        onClick = {
                            onEvent(
                                UsersScreenEvent.OnChangeRoleUser(
                                    role = role,
                                    user = user
                                )
                            )
                        }
                    )
                    Text(
                        text = role,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy)),
                            fontWeight = FontWeight(600),
                            color = baseText,
                        )
                    )
                }
            }
        }
    }
}