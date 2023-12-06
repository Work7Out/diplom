package diplom.gorinych.ui.presentation.user.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.ui.presentation.base.AppBarUser
import diplom.gorinych.ui.presentation.base.BottomBarUser
import diplom.gorinych.ui.theme.grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsUserScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NewsUserScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBarUser(
                navController = navController,
                onSendCall = {
                    onEvent(NewsUserScreenEvent.OnSendCall)
                })
        },
        bottomBar = {
            BottomBarUser(
                navController = navController,
                idUser = state.value.user?.id ?: -1
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = grey)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.value.news) { note ->
                ItemUserNote(note = note)
            }
        }
    }
}
