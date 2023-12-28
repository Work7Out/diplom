package diplom.gorinych.ui.presentation.user.house_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
import coil.compose.AsyncImage
import diplom.gorinych.R
import diplom.gorinych.domain.utils.BASE_URL_IMAGE
import diplom.gorinych.ui.presentation.base.ItemFeedback
import diplom.gorinych.ui.presentation.base.LoadingScreen
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.grey
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.white
import diplom.gorinych.ui.theme.yellow
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.LocalDate
import java.util.Locale

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
    val promo = remember {
        mutableStateOf("")
    }
    if (isShowDialog.value) {
        Dialog(onDismissRequest = { isShowDialog.value = false }) {
            FeedbackDialog(
                isShowDialog = isShowDialog,
                onEvent = onEvent
            )
        }
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
        if (state.value.isLoading) {
            LoadingScreen(
                paddingValues = padding
            )
        } else {
            Column(
                modifier = modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .background(color = grey)
                    .padding(10.dp),
            ) {
                AsyncImage(
                    modifier = modifier.fillMaxWidth(),
                    model = BASE_URL_IMAGE + state.value.house?.image,
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
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
                    monthHeader = {
                        MonthHeader(monthState = it)
                    },
                    calendarState = calendarState,
                    dayContent = {
                        ItemDay(
                            state = it
                        )
                    }
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
                Spacer(modifier = modifier.height(5.dp))
                LazyVerticalGrid(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(10.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(state.value.additions) { addon ->
                        ItemAddon(
                            addon = addon,
                            isSelected = state.value.additionsSelected.contains(addon),
                            onEvent = onEvent
                        )
                    }
                }
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = "${stringResource(id = R.string.amount_reserve)} ${state.value.amountReserve} ${
                        stringResource(
                            id = R.string.byn
                        )
                    }",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.choose_promo),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    ),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = modifier.weight(8f),
                        value = promo.value,
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.add_promo),
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.gilroy)),
                                    fontWeight = FontWeight(500),
                                    color = secondText,
                                )
                            )
                        },
                        onValueChange = {
                            promo.value = it
                        })
                    Spacer(modifier = modifier.width(5.dp))
                    Button(
                        modifier = modifier.weight(1f),
                        onClick = {
                            onEvent(HouseDetailEvent.CheckPromo(promo.value))
                            promo.value = ""
                        },
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = blue
                        ),
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_forward_24),
                            contentDescription = "",
                            tint = white
                        )
                    }
                }
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = "${stringResource(id = R.string.percent_discount)} ${state.value.promo?.valueDiscount?:0}",
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
                    enabled = state.value.isEnableFeedback,
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
}


@Composable
fun MonthHeader(
    monthState: MonthState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier.testTag("Decrement"),
            onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                tint = baseText,
                contentDescription = "Previous",
            )
        }
        Text(
            modifier = Modifier.testTag("MonthLabel"),
            text = monthState.currentMonth.month
                .getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
                .lowercase()
                .replaceFirstChar { it.titlecase() },
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText
            ),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = monthState.currentMonth.year.toString(),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText
            ),
        )
        IconButton(
            modifier = Modifier.testTag("Increment"),
            onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                tint = baseText,
                contentDescription = "Next",
            )
        }
    }
}

@Composable
fun <T : SelectionState> ItemDay(
    state: DayState<T>,
    modifier: Modifier = Modifier,
    selectionColor: Color = yellow,
    currentDayColor: Color = baseText,
    onClick: (LocalDate) -> Unit = {},
) {
    val date = state.date
    val selectionState = state.selectionState

    val isSelected = selectionState.isDateSelected(date)

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp),
        elevation = CardDefaults.cardElevation(
            if (state.isFromCurrentMonth) 4.dp else 0.dp
        ),
        border = if (state.isCurrentDay) BorderStroke(1.dp, currentDayColor) else null,
        colors = CardDefaults.cardColors(
            containerColor = blue,
            contentColor = if (isSelected) selectionColor else contentColorFor(
                backgroundColor = white
            )
        )
    ) {
        Box(
            modifier = modifier
                .clickable {
                    onClick(date)
                    selectionState.onDateSelected(date)
                }
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = modifier.align(alignment = Alignment.Center),
                text = date.dayOfMonth.toString(),
            )
        }
    }
}