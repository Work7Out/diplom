package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diplom.gorinych.domain.model.House
import diplom.gorinych.ui.theme.PurpleGrey80

@Composable
fun ItemHouse(
    modifier: Modifier = Modifier,
    house: House,
    onClick: ()-> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PurpleGrey80)
            .clickable(onClick = onClick)
    ) {
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(id = house.image),
            contentDescription = ""
        )
        Spacer(modifier = modifier.width(10.dp))
        Column(
            modifier = modifier.weight(1f)
        ) {
            Text(
                text = house.name,
                fontWeight = FontWeight(600),
                fontSize = 20.sp
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = "Цена ${house.price} BYN"
            )
        }
    }
}
/*@Preview
@Composable
private fun SampleItemHouse() {
    ItemHouse(
        house = House(
            id = 1,
            name = "FFGG",
            description = "dfggvnosvsn  sdvdvdv",
            price = 123.0,
            image = R.drawable.image
        )
    )
}*/
