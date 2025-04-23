package com.jesil.skycast.features.weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jesil.skycast.features.weather.data.WeatherDataUi
import com.jesil.skycast.features.weather.data.WeatherStateUi
import com.jesil.skycast.features.weather.data.convertToCelsius
import com.jesil.skycast.features.weather.presentation.components.LocationTopBar
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.generateBackgroundColor
import com.jesil.skycast.ui.util.generateIcon

@Composable
fun WeatherScreen(
    state: WeatherStateUi,
    modifier: Modifier = Modifier
) {
    val backgroundColor = state.weatherData?.weatherTypeIcon?.generateBackgroundColor()
    ConstraintLayout(
        modifier = modifier
            .background(backgroundColor!!)
            .fillMaxSize()
    ) {
        val (topBar, locationIcon, location, weatherType, temperature, weatherTypeDescription) = createRefs()

        LocationTopBar(
            date = state.weatherData.time,
            backgroundColor = backgroundColor,
            onNavigationClick = {},
            modifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                }
        )

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = state.weatherData.location,
            tint = Color.White,
            modifier = Modifier
                .constrainAs(locationIcon) {
                    top.linkTo(topBar.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = state.weatherData.location,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(location) {
                top.linkTo(locationIcon.bottom, margin = 5.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Image(
            painter = painterResource(state.weatherData.weatherTypeIcon.generateIcon()),
            contentDescription = state.weatherData.weatherType,
            modifier = Modifier
                .size(250.dp)
                .constrainAs(weatherType) {
                    top.linkTo(location.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = state.weatherData.temperature,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            fontSize = 80.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(temperature) {
                top.linkTo(weatherType.bottom, margin = 10.dp)
                start.linkTo(parent.start, margin = 30.dp)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = state.weatherData.weatherTypeDescription,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(weatherTypeDescription) {
                    top.linkTo(temperature.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        
    }

}

@Preview
@Composable
private fun WeatherScreenPreview() {
    SkyCastTheme {
        WeatherScreen(
            state = WeatherStateUi(
                weatherData = WeatherDataUi(
                    location = "Lagos, NG",
                    temperature = 303.62.convertToCelsius(),
                    weatherType = "Clear sky",
                    weatherTypeDescription = "clear sky",
                    weatherTypeIcon = "01d",
                    windSpeed = "1.44",
                    humidity = "94",
                    rainChance = "0.13",
                    timeZone = "West Africa Time",
                    time = "Mon, July 6"
                ),
                isLoading = false,
                error = null
            )
        )

    }

}