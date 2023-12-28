package diplom.gorinych.ui.presentation.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.ui.theme.blue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    scope.launch {
        delay(2000)
        navController.navigate("loginScreen")
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = blue)
            .padding(10.dp)
    ) {
        Image(
            modifier = modifier
                .size(200.dp)
                .align(alignment = Alignment.Center),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = ""
        )
    }
}