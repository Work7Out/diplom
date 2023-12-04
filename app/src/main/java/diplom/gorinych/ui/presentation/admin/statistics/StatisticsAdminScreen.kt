package diplom.gorinych.ui.presentation.admin.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.ui.presentation.base.AppBarAdmin
import diplom.gorinych.ui.presentation.base.BottomBarAdmin
import diplom.gorinych.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsAdminScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBarAdmin(
                navController = navController)
        },
        bottomBar = {
            BottomBarAdmin(
                navController = navController,
                idUser = state.value.idUser,
                count = state.value.countNewReserves
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = PurpleGrey80)
                .padding(padding)
        ) {
            Text(text = "Всего заказов - ${state.value.countOrders}")
            Spacer(modifier = modifier.height(5.dp))
            Text(text = "Из них подтвержденных - ${state.value.countConfirmOrders}")
            Spacer(modifier = modifier.height(5.dp))
            Text(text = "Всего заработано - ${state.value.amountAll} BYN")
            Spacer(modifier = modifier.height(5.dp))
            Text(text = "За последний месяц - ${state.value.amountLastMonth} BYN")
            Spacer(modifier = modifier.height(5.dp))
            Text(text = "За год - ${state.value.amountLastSeason} BYN")
        }
    }
}