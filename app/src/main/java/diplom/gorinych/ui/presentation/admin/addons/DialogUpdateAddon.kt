package diplom.gorinych.ui.presentation.admin.addons


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diplom.gorinych.R
import diplom.gorinych.domain.utils.isNumeric
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.lightGrey
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogUpdateAddon(
    modifier: Modifier = Modifier,
    title:String,
    price: Double,
    id: Int,
    isShowDialog: MutableState<Boolean>,
    onEvent: (AddonScreenEvent) -> Unit
) {
    val titleAddon = remember { mutableStateOf(title) }
    val priceAddon = remember { mutableStateOf(price.toString()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = lightGrey)
            .padding(10.dp)
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = titleAddon.value,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_name_addon),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(500),
                        color = secondText,
                    )
                )
            },
            onValueChange = {
                titleAddon.value = it
            }
        )
        Spacer(modifier = modifier.height(10.dp))
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = priceAddon.value,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_price_addon),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(500),
                        color = secondText,
                    )
                )
            },
            onValueChange = { text ->
                priceAddon.value = text
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = modifier.height(26.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally),
            onClick = {
                onEvent(
                    AddonScreenEvent.UpdateAddon(
                        id = id,
                        title = titleAddon.value,
                        price = priceAddon.value.toDouble()
                    )
                )
                isShowDialog.value = false
            },
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            ),
            enabled = titleAddon.value.isNotEmpty()
                    && isNumeric(priceAddon.value)
        ) {
            Text(
                text = stringResource(id = R.string.update_addon),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(700),
                    color = white,
                )
            )
        }
    }
}