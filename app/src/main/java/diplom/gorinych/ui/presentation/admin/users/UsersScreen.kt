package diplom.gorinych.ui.presentation.admin.users

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
import diplom.gorinych.ui.presentation.admin.addons.AddonScreenEvent
import diplom.gorinych.ui.presentation.base.AppBarAdmin
import diplom.gorinych.ui.presentation.base.BottomBarAdmin
import diplom.gorinych.ui.presentation.base.LoadingScreen
import diplom.gorinych.ui.presentation.user.settings.SettingsEvent
import diplom.gorinych.ui.theme.grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBarAdmin(
                navController = navController,
                onClick = { onEvent(UsersScreenEvent.Exit) }
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
            LazyColumn(
                modifier = modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .background(color = grey)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.value.users) { user ->
                    ItemUser(
                        user = user,
                        idUser = state.value.idUser,
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}