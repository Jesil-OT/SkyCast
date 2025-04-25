package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jesil.skycast.R
import com.jesil.skycast.ui.theme.ClearSky
import com.jesil.skycast.ui.theme.SkyCastTheme

@Composable
fun WeatherInfoItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    iconRes: Painter
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (titleText, valueText, icon) = createRefs()

        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 14.sp,
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        Icon(
            painter = iconRes,
            contentDescription = title,
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(15.dp).constrainAs(icon) {
                start.linkTo(titleText.end, margin = 5.dp)
                top.linkTo(titleText.top)
                bottom.linkTo(titleText.bottom)
            }
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.constrainAs(valueText) {
                top.linkTo(titleText.bottom, margin = 5.dp)
                start.linkTo(titleText.start)
            }
        )
    }
}

@Preview
@Composable
private fun WeatherInfoItemPreview() {
    SkyCastTheme {
        WeatherInfoItem(
            title = "Humidity",
            value = "80%",
            iconRes = painterResource(id = R.drawable.ic_humidity),
            modifier = Modifier.background(ClearSky)
        )
    }
}