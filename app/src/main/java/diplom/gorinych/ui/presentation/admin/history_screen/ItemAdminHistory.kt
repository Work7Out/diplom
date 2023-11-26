package diplom.gorinych.ui.presentation.admin.history_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.domain.utils.ACTIVE
import diplom.gorinych.domain.utils.INACTIVE
import diplom.gorinych.domain.utils.WAITING_CONFIRM
import diplom.gorinych.ui.theme.PurpleGrey80

@Composable
fun ItemAdminHistory(
    modifier: Modifier = Modifier,
    reserve: Reserve,
    onEvent: (HistoryScreenEvent) -> Unit
) {

    val statuses = listOf(INACTIVE, WAITING_CONFIRM, ACTIVE)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PurpleGrey80)
            .padding(5.dp)
    ) {
        Text(text = "логин - ${reserve.idUser}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "дом - ${reserve.idHouse}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "начало - ${reserve.dateBegin}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "конец - ${reserve.dateEnd}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "сумма - ${reserve.amount}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "статус регистрации - ${reserve.confirmReservation}")
        Spacer(modifier = modifier.height(5.dp))
        statuses.forEach { status ->
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                RadioButton(
                    selected = (status == reserve.confirmReservation),
                    onClick = {
                        onEvent(
                            HistoryScreenEvent.OnConfirmReserve(
                                reserve = reserve,
                                status = status
                            )
                        )
                    }
                )
                Text(text = status)
            }
        }
    }
}