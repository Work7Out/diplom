package diplom.gorinych.ui.presentation.admin.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.ui.presentation.admin.addons.AddonScreenEvent
import diplom.gorinych.ui.presentation.base.AppBarAdmin
import diplom.gorinych.ui.presentation.base.BottomBarAdmin
import diplom.gorinych.ui.presentation.base.LoadingScreen
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsAdminScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBarAdmin(
                navController = navController,
                onClick = { onEvent(StatisticScreenEvent.Exit) }
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
                    .padding(10.dp),
            ) {
                Text(
                    text = "${stringResource(id = R.string.all_orders)} ${state.value.countOrders}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    ),
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    text = "${stringResource(id = R.string.confirm_orders)} ${state.value.countConfirmOrders}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    ),
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    text = "${stringResource(id = R.string.amount)} ${
                        String.format(
                            "%.2f",
                            state.value.amountAll
                        )
                    } ${
                        stringResource(
                            id = R.string.byn
                        )
                    }",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    )
                )
                Spacer(modifier = modifier.height(5.dp))

                Text(
                    text = "${stringResource(id = R.string.amount_last_month)} ${
                        String.format(
                            "%.2f",
                            state.value.amountLastMonth
                        )
                    } ${
                        stringResource(
                            id = R.string.byn
                        )
                    }",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    )
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    text = "${stringResource(id = R.string.amount_last_year)} ${
                        String.format(
                            "%.2f",
                            state.value.amountLastSeason
                        )
                    } ${
                        stringResource(
                            id = R.string.byn
                        )
                    }",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    )
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.history_calls),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(5.dp))
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.value.calls) { call ->
                        ItemCall(call = call)
                    }
                }
            }
        }
    }
}