package diplom.gorinych.ui.presentation.user.history_user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.ui.presentation.base.ItemHistory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryUserScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HistoryUserScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                    navController.navigate("listHousesUserScreen/${state.value.idUser}")
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_house_24),
                        contentDescription = ""
                    )
                }
                IconButton(onClick = {
                    navController.navigate("historyUserScreen/${state.value.idUser}")
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_history_24),
                        contentDescription = ""
                    )
                }
                IconButton(onClick = {
                    navController.navigate("loginScreen") {
                        this.popUpTo("loginScreen") {
                            inclusive = true
                        }
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                        contentDescription = ""
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .padding(padding)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.value.reserves) { reserve ->
                ItemHistory(
                    reserve = reserve,
                    onEvent = onEvent)
            }
        }
    }
}