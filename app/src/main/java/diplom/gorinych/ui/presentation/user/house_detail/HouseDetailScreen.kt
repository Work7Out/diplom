package diplom.gorinych.ui.presentation.user.house_detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.ui.presentation.base.ItemFeedback
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.grey
import diplom.gorinych.ui.theme.white
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "",
                        tint = baseText
                    )
                }
                Spacer(modifier = modifier.width(10.dp))
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = state.value.house?.name ?: "",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    ),
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(color = grey)
                .padding(10.dp),
        ) {
            Image(
                modifier = modifier.fillMaxWidth(),
                painter = painterResource(id = state.value.house?.image ?: R.drawable.image),
                contentDescription = ""
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(
                text = "${stringResource(id = R.string.price)} ${state.value.house?.price} ${
                    stringResource(
                        id = R.string.byn
                    )
                }",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = blue
                )
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = state.value.house?.description ?: "",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = baseText
                )
            )
            Spacer(modifier = modifier.height(5.dp))
            LazyRow(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp),
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
                text = stringResource(id = R.string.choose_dates),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = baseText
                ),
                textAlign = TextAlign.Center
            )
            SelectableCalendar(
                modifier = modifier
                    .fillMaxWidth(),
                calendarState = calendarState,
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(id = R.string.choose_addons),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = baseText
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(10.dp))
            Button(
                modifier = modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue
                ),
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
                    text = stringResource(id = R.string.do_reserve),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(700),
                        color = white,
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = modifier.height(10.dp))
            Button(
                modifier = modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue
                ),
                onClick = {
                    isShowDialog.value = true
                }) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.do_feedback),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(700),
                        color = white,
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}