package diplom.gorinych.ui.presentation.admin.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.ui.presentation.base.AppBarAdmin
import diplom.gorinych.ui.presentation.base.BottomBarAdmin
import diplom.gorinych.ui.presentation.base.LoadingScreen
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.grey
import diplom.gorinych.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NewsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    val isShowDialog = remember { mutableStateOf(false) }

    if (isShowDialog.value) {
        Dialog(onDismissRequest = { isShowDialog.value = false }) {
            DialogNewNote(
                isShowDialog = isShowDialog,
                onEvent = onEvent
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBarAdmin(
                navController = navController,
            )
        },
        bottomBar = {
            BottomBarAdmin(
                navController = navController,
                idUser = state.value.idUser,
                count = state.value.countNewReserves
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isShowDialog.value = true
                },
                containerColor = blue
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_add_24),
                    contentDescription = null,
                    tint = white
                )
            }
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
                    .fillMaxSize()
                    .background(color = grey)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.value.news) { note ->
                    ItemNote(
                        note = note,
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}