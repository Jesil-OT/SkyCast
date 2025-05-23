package com.jesil.skycast.features.weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jesil.skycast.R
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.features.weather.models.WeatherViewState
import com.jesil.skycast.features.weather.presentation.components.ErrorScreen
import com.jesil.skycast.features.weather.presentation.components.HoursWeatherItem
import com.jesil.skycast.features.weather.presentation.components.LoadingScreen
import com.jesil.skycast.features.weather.presentation.components.LocationTopBar
import com.jesil.skycast.features.weather.presentation.components.WeatherAction
import com.jesil.skycast.features.weather.presentation.components.WeatherInfo
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.generateBackgroundColor
import com.jesil.skycast.ui.util.generateIcon

@Composable
fun WeatherScreen(
    state: WeatherViewState,
    onActions: (WeatherAction) -> Unit,
) {
    when (state) {
        is WeatherViewState.Error -> {
            ErrorScreen(
                message = state.message,
                onRetryClick = { onActions(WeatherAction.Retry) }
            )
        }

        is WeatherViewState.Idle -> {}

        is WeatherViewState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }

        is WeatherViewState.Success -> {
            WeatherInnerScreen(
                state = state.data
            )
        }
    }
}

@Composable
fun WeatherInnerScreen(
    state: WeatherStateUi,
    modifier: Modifier = Modifier
) {
    val backgroundColor = state.weatherTypeIcon.generateBackgroundColor()
    Scaffold(
        topBar = {
            LocationTopBar(
                modifier = Modifier.padding(top = 30.dp),
                date = state.time,
                onNavigationClick = {}
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = modifier
                .background(
                    Brush.verticalGradient(
                        colors = backgroundColor
                    )
                )
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    state = rememberScrollState()
                )
        ) {
            val (locationIcon, location, weatherType, temperature, weatherTypeDescription, feelLike,
                hourlyWeather, weatherInfo) = createRefs()


            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = state.location,
                tint = Color.White,
                modifier = Modifier
                    .constrainAs(locationIcon) {
                        top.linkTo(location.top)
                        end.linkTo(location.start)
                        bottom.linkTo(location.bottom)
                    }
            )

            Text(
                text = state.location,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(location) {
                    top.linkTo(parent.top, margin = 20.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start, margin = 20.dp)
                }
            )
            Image(
                painter = painterResource(state.weatherTypeIcon.generateIcon()),
                contentDescription = state.weatherType,
                modifier = Modifier
                    .size(250.dp)
                    .constrainAs(weatherType) {
                        top.linkTo(location.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = state.temperature,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                fontSize = 80.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(temperature) {
                        top.linkTo(weatherType.bottom)
                        start.linkTo(parent.start, margin = 30.dp)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = state.weatherTypeDescription,
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(weatherTypeDescription) {
                        top.linkTo(temperature.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = stringResource(R.string.feels_like, state.minTemperature),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(feelLike) {
                        top.linkTo(weatherTypeDescription.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Surface(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .constrainAs(hourlyWeather) {
                    top.linkTo(feelLike.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                shape = RoundedCornerShape(12.dp),
                color = Color.White.copy(.2f),
                tonalElevation = 100.dp
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.hourlyWeather) { hourlyWeather ->
                        HoursWeatherItem(
                            time = hourlyWeather.time,
                            weatherType = hourlyWeather.weatherTypeIcon,
                            temperature = hourlyWeather.temperature,
                            minTemperature = hourlyWeather.minTemperature,
                        )
                    }
                }
            }

            WeatherInfo(
                data = state,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .constrainAs(weatherInfo) {
                        top.linkTo(hourlyWeather.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 30.dp)
                    }
            )
        }
    }
}


@Preview
@Composable
private fun WeatherScreenPreview() {
    SkyCastTheme {
        WeatherScreen(
            state = WeatherViewState.Success(
                data = WeatherStateUi(
                    location = "Lagos, NG",
                    temperature = "31°",
                    weatherType = "Clear sky",
                    weatherTypeDescription = "clear sky",
                    weatherTypeIcon = "01d",
                    windSpeed = "11 km/h",
                    humidity = "94%",
                    rainChance = "0.13",
                    timeZone = "West Africa Time",
                    time = "Mon, July 6",
                    sunrise = "3:55 am",
                    sunset = "7:00 pm",
                    pressure = "1009 hpa",
                    minTemperature = "29°C",
                )
            ),
            onActions = {}
        )

    }
}