package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.ui.presentation.user.house_detail.HouseDetailEvent.AddFeedback
import diplom.gorinych.ui.theme.PurpleGrey80

@Composable
fun ItemHistory(
    modifier: Modifier = Modifier,
    reserve: Reserve
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PurpleGrey80)
            .padding(5.dp)
    ) {
        Text(
            text = "${reserve.dateBegin} - ${reserve.dateEnd}"
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "статус: ${reserve.confirmReservation}"
        )
        Spacer(modifier = modifier.height(5.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = {

            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Удалить бронь",
            )
        }
    }
}