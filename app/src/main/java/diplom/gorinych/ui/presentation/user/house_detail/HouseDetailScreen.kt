package diplom.gorinych.ui.presentation.user.house_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.ui.presentation.base.ItemFeedback
import diplom.gorinych.ui.theme.Purple40

@Composable
fun HouseDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HouseDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
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
            text = "Цена ${state.value.house?.price} Р",
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
            items(state.value.house?.feedbacks ?: emptyList()) {
                ItemFeedback(
                    feedback = it,
                    name = state.value.nameUser
                )
            }
        }
    }
}