package com.jesil.skycast.features.weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jesil.skycast.features.weather.data.HoursWeatherStateUi
import com.jesil.skycast.features.weather.data.WeatherDataUi
import com.jesil.skycast.features.weather.data.WeatherStateUi
import com.jesil.skycast.features.weather.data.convertToCelsius
import com.jesil.skycast.features.weather.presentation.components.HoursWeatherItem
import com.jesil.skycast.features.weather.presentation.components.LocationTopBar
import com.jesil.skycast.features.weather.presentation.components.WeatherInfo
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.generateBackgroundColor
import com.jesil.skycast.ui.util.generateIcon

@Composable
fun WeatherScreen(
    state: WeatherStateUi,
    modifier: Modifier = Modifier
) {
    val backgroundColor = state.weatherData?.weatherTypeIcon?.generateBackgroundColor()

    Scaffold(
        topBar = {
            LocationTopBar(
                modifier = Modifier.padding(top = 30.dp),
                date = state.weatherData!!.time,
                onNavigationClick = {}
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = modifier
                .background(
                    Brush.verticalGradient(
                        colors = backgroundColor!!
                    )
                )
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    state = rememberScrollState()
                )
        ) {
            val (locationIcon, location, weatherType, temperature, weatherTypeDescription,
                divider1, hourlyWeather, divider2, weatherInfo) = createRefs()


            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = state.weatherData.location,
                tint = Color.White,
                modifier = Modifier
                    .constrainAs(locationIcon) {
                        top.linkTo(parent.top, margin = 30.dp)
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

            HorizontalDivider(
                color = Color.White.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.constrainAs(divider1) {
                    top.linkTo(weatherTypeDescription.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .constrainAs(hourlyWeather) {
                        top.linkTo(weatherTypeDescription.bottom, margin = 50.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.weatherData.hourlyWeather) { hourlyWeather ->
                    HoursWeatherItem(
                        time = hourlyWeather.time,
                        weatherType = hourlyWeather.weatherTypeIcon,
                        temperature = hourlyWeather.temperature,
                        minTemperature = hourlyWeather.minTemperature,
                    )
                }
            }

            HorizontalDivider(
                color = Color.White.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.constrainAs(divider2) {
                    top.linkTo(hourlyWeather.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            WeatherInfo(
                data = state.weatherData,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .constrainAs(weatherInfo) {
                        top.linkTo(divider2.bottom, margin = 30.dp)
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
            state = WeatherStateUi(
                weatherData = WeatherDataUi(
                    location = "Lagos, NG",
                    temperature = 303.62.convertToCelsius(),
                    weatherType = "Clear sky",
                    weatherTypeDescription = "clear sky",
                    weatherTypeIcon = "03d",
                    windSpeed = "11 km/h",
                    humidity = "94%",
                    rainChance = "0.13",
                    timeZone = "West Africa Time",
                    time = "Mon, July 6",
                    sunrise = "3:55 am",
                    sunset = "7:00 pm",
                    pressure = "1009 hpa",
                    minTemperature = "29°C",
                    hourlyWeather = dummyHourlyData
                ),
                isLoading = false,
                error = null
            )
        )

    }
}

internal val dummyHourlyData = listOf(
    HoursWeatherStateUi(
        time = "Now",
        weatherTypeIcon = "01n",
        temperature = 292.46.convertToCelsius(),
        minTemperature = 290.31.convertToCelsius()
    ),
    HoursWeatherStateUi(
        time = "3PM",
        weatherTypeIcon = "02n",
        temperature = 292.46.convertToCelsius(),
        minTemperature = 290.31.convertToCelsius()
    ),
    HoursWeatherStateUi(
        time = "6PM",
        weatherTypeIcon = "01d",
        temperature = 292.46.convertToCelsius(),
        minTemperature = 290.31.convertToCelsius()
    ),
    HoursWeatherStateUi(
        time = "9PM",
        weatherTypeIcon = "04n",
        temperature = 292.46.convertToCelsius(),
        minTemperature = 290.31.convertToCelsius()
    ),
    HoursWeatherStateUi(
        time = "12AM",
        weatherTypeIcon = "01n",
        temperature = 292.46.convertToCelsius(),
        minTemperature = 290.31.convertToCelsius()
    ),
    HoursWeatherStateUi(
        time = "3AM",
        weatherTypeIcon = "11n",
        temperature = 292.46.convertToCelsius(),
        minTemperature = 290.31.convertToCelsius()
    ),
    HoursWeatherStateUi(
        time = "6AM",
        weatherTypeIcon = "02d",
        temperature = 292.46.convertToCelsius(),
        minTemperature = 290.31.convertToCelsius()
    ),
)