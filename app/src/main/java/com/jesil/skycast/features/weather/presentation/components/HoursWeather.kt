package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jesil.skycast.features.weather.data.HoursWeatherStateUi
import com.jesil.skycast.features.weather.data.convertToCelsius
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.generateBackgroundColor
import com.jesil.skycast.ui.util.generateIcon

@Composable
fun HoursWeatherItem(
    modifier: Modifier = Modifier,
    time: String,
    weatherType: String,
    temperature: String,
    minTemperature: String
) {
    ConstraintLayout(
        modifier = modifier
    ){
        val (timeText, weatherTypeIcon, temperatureText, minTemperatureText) = createRefs()

        Text(
            text = time,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.constrainAs(timeText) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Image(
            painter = painterResource(weatherType.generateIcon()),
            contentDescription = weatherType,
            modifier = Modifier
                .size(40.dp)
                .constrainAs(weatherTypeIcon) {
                top.linkTo(timeText.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = temperature,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(temperatureText) {
                top.linkTo(weatherTypeIcon.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = minTemperature,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 15.sp,
            modifier = Modifier.constrainAs(minTemperatureText) {
                top.linkTo(temperatureText.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Preview
@Composable
private fun HoursWeatherItemPreview() {
    SkyCastTheme {
        HoursWeatherItem(
            time = "Now",
            weatherType = "01n",
            temperature = 292.46.convertToCelsius(),
            minTemperature = 290.31.convertToCelsius(),
            modifier = Modifier.background(
                Color.Gray
            )
        )
    }
}