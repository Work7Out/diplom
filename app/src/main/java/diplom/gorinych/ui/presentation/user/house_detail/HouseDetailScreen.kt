package diplom.gorinych.ui.presentation.user.house_detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.ui.presentation.base.ItemFeedback
import diplom.gorinych.ui.theme.Purple40
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode

@Composable
fun HouseDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HouseDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    val context = LocalContext.current
    val calendarState: CalendarState<DynamicSelectionState> = rememberSelectableCalendarState(
        initialSelectionMode = SelectionMode.Period,
        initialSelection = listOf()
    )
    val isShowDialog = remember {
        mutableStateOf(false)
    }
    if (isShowDialog.value) {
        Dialog(onDismissRequest = { isShowDialog.value = false }) {
            FeedbackDialog(
                isShowDialog = isShowDialog,
                onEvent = onEvent
            )
        }
    }
    if (!state.value.message.isNullOrBlank()) {
        Toast.makeText(
            context,
            state.value.message,
            Toast.LENGTH_LONG
        ).show()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    ) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "",
                tint = Purple40
            )
        }
        Spacer(modifier = modifier.height(10.dp))
        Text(
            modifier = modifier.fillMaxWidth(),
            text = state.value.house?.name ?: "",
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Spacer(modifier = modifier.height(5.dp))
        Image(
            modifier = modifier.fillMaxWidth(),
            painter = painterResource(id = state.value.house?.image ?: R.drawable.image),
            contentDescription = ""
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "Цена ${state.value.house?.price} BYN",
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            modifier = modifier.fillMaxWidth(),
            text = state.value.house?.description ?: "",
        )
        Spacer(modifier = modifier.height(5.dp))
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.value.feedbacks) {
                ItemFeedback(
                    feedback = it,
                    name = state.value.nameUser
                )
            }
        }
        Spacer(modifier = modifier.height(5.dp))
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "Выберите даты для бронирования",
        )
        SelectableCalendar(
            modifier = modifier
                .fillMaxWidth(),
            calendarState = calendarState,
        )
        Spacer(modifier = modifier.height(5.dp))
        //calendarState.selectionState.selection.first()
        Button(
            modifier = modifier
                .fillMaxWidth(),
            enabled = calendarState.selectionState.selection.isNotEmpty(),
            onClick = {
                onEvent(
                    HouseDetailEvent.AddReserve(
                        dateBegin = calendarState.selectionState.selection.first(),
                        dateEnd = calendarState.selectionState.selection.last(),
                        valueDays = calendarState.selectionState.selection.size,
                        addons = emptyList() //TODO
                    )
                )
                calendarState.selectionState.selection = emptyList()

            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Забронировать",
            )
        }
        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = {
                isShowDialog.value = true
            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Оставить отзыв",
            )
        }
    }
}