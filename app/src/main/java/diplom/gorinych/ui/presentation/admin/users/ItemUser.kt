package diplom.gorinych.ui.presentation.admin.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diplom.gorinych.domain.model.User
import diplom.gorinych.ui.presentation.user.house_detail.UsersScreenEvent
import diplom.gorinych.ui.theme.PurpleGrey80

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
            .background(color = PurpleGrey80)
            .padding(5.dp)
    ) {
        Text(text = "логин - ${user.name}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "пароль - ${user.password}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "роль - ${user.role}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = if (user.isBlocked) "Заблокирован" else "Не заблокирован")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "телефон - ${user.phone}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "email - ${user.email}")
        Spacer(modifier = modifier.height(5.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(),
            enabled = idUser != user.id,
            onClick = {
                onEvent(UsersScreenEvent.OnChangeStatusBlock(user))
            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = if (user.isBlocked) "Разблокировать" else "Заблокировать",
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
                        onClick = {
                            onEvent(
                                UsersScreenEvent.OnChangeRoleUser(
                                    role = role,
                                    user = user
                                )
                            )
                        }
                    )
                    Text(text = role)
                }
            }
        }
    }
}