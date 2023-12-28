package diplom.gorinych.ui.presentation.admin.history_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.domain.utils.ACTIVE
import diplom.gorinych.domain.utils.INACTIVE
import diplom.gorinych.domain.utils.WAITING_CONFIRM
import diplom.gorinych.ui.presentation.admin.addons.AddonScreenEvent
import diplom.gorinych.ui.presentation.base.AppBarAdmin
import diplom.gorinych.ui.presentation.base.BottomBarAdmin
import diplom.gorinych.ui.presentation.base.LoadingScreen
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryAdminScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBarAdmin(
                navController = navController,
                onClick = { onEvent(HistoryScreenEvent.Exit) }
            )
        },
        bottomBar = {
            BottomBarAdmin(
                navController = navController,
                idUser = state.value.idUser,
                count = state.value.countNewReserves
            )
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
                    .fillMaxSize()
                    .background(color = grey)
                    .padding(horizontal = 10.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = {
                        onEvent(HistoryScreenEvent.OnChangeHistoryState(HistoryState.ReserveState))
                    }) {
                        Text(
                            text = stringResource(id = R.string.history_reserve),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.gilroy)),
                                fontWeight = FontWeight(600),
                                color = if (state.value.historyState is HistoryState.ReserveState) blue else baseText,
                            )
                        )
                    }
                    TextButton(onClick = {
                        onEvent(HistoryScreenEvent.OnChangeHistoryState(HistoryState.FeedbackState))
                    }) {
                        Text(
                            text = stringResource(id = R.string.history_feedback),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.gilroy)),
                                fontWeight = FontWeight(600),
                                color = if (state.value.historyState is HistoryState.FeedbackState) blue else baseText,
                            )
                        )
                    }
                }
                when (state.value.historyState) {
                    HistoryState.FeedbackState -> {
                        LazyColumn(
                            modifier = modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(state.value.feedbacks) { feedback ->
                                ItemFeedbackScreen(
                                    feedback = feedback,
                                    onEvent = onEvent
                                )
                            }
                        }
                    }

                    HistoryState.ReserveState -> {
                        LazyColumn(
                            modifier = modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(state.value.reserves) { reserve ->
                                val statuses = listOf(INACTIVE, WAITING_CONFIRM, ACTIVE)
                                ItemAdminHistory(
                                    reserve = reserve,
                                    statuses = statuses,
                                    onEvent = onEvent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}