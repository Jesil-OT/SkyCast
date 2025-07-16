package com.jesil.skycast.features.weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesil.skycast.R
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.features.weather.models.WeatherViewState
import com.jesil.skycast.features.weather.presentation.components.DailyWeatherItem
import com.jesil.skycast.features.weather.presentation.components.ErrorScreen
import com.jesil.skycast.features.weather.presentation.components.HoursWeatherItem
import com.jesil.skycast.features.weather.presentation.components.LazyListHeader
import com.jesil.skycast.features.weather.presentation.components.LoadingScreen
import com.jesil.skycast.features.weather.presentation.components.LocationTopBar
import com.jesil.skycast.features.weather.presentation.components.WeatherInfos
import com.jesil.skycast.features.weather.presentation.events.WeatherAction
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.generateBackgroundColor
import com.jesil.skycast.ui.util.generateIcon
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

@Composable
fun WeatherScreen(
    state: WeatherViewState,
    onActions: (WeatherAction) -> Unit,
) {
    Timber.d("WeatherScreen!!!!!: The state is $state")
    when (state) {
        is WeatherViewState.Error -> {
            ErrorScreen(
                message = state.message,
                onRetryClick = { onActions(WeatherAction.Retry) }
            )
        }

        is WeatherViewState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }

        is WeatherViewState.Success -> {
            WeatherInnerScreen(
                state = state.data,
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
                    Brush.verticalGradient(colors = backgroundColor)
                )
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(state = rememberScrollState())
        ) {
            val (locationIcon, location, weatherType, temperature, weatherTypeDescription, feelLike,
                hourlyWeather, dailyWeather, weatherInfo) = createRefs()
            val daysCount = state.dailyWeather.size.toString()

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
                fontSize = 15.sp,
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
                    .size(160.dp)
                    .constrainAs(weatherType) {
                        top.linkTo(location.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = state.temperature,
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
                fontSize = 70.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(temperature) {
                        top.linkTo(weatherType.bottom)
                        start.linkTo(parent.start,)
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
                    .padding(horizontal = 10.dp)
                    .constrainAs(hourlyWeather) {
                        top.linkTo(feelLike.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.background.copy(.2f),
                tonalElevation = 100.dp
            ) {
                Column {
                    LazyListHeader(
                        modifier = Modifier.fillMaxWidth(),
                        iconRes = ImageVector.vectorResource(R.drawable.outline_access_time),
                        headerTitle = {
                            Text(
                                text = stringResource(R.string.hourly_forecast),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White.copy(.5f),
                                    fontSize = 13.sp,
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
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
            }

            Surface(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .constrainAs(dailyWeather) {
                        top.linkTo(hourlyWeather.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.background.copy(.2f),
                tonalElevation = 100.dp
            ) {
                LazyColumn(
                    modifier = Modifier.height(350.dp),
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    item {
                        LazyListHeader(
                            modifier = Modifier.fillMaxWidth(),
                            iconRes = Icons.Outlined.DateRange,
                            headerTitle = {
                                Text(
                                    text = stringResource(R.string.days_forecast, daysCount),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White.copy(.5f),
                                        fontSize = 13.sp,
                                    )
                                )
                            }
                        )

                    }

                    items(state.dailyWeather) { dailyWeather ->
                        DailyWeatherItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            day = dailyWeather.day,
                            weatherType = dailyWeather.weatherTypeIcon,
                            temperature = dailyWeather.temperature,
                            minTemperature = dailyWeather.minTemperature,
                        )
                    }
                }
            }

            WeatherInfos(
                data = state,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .constrainAs(weatherInfo) {
                        top.linkTo(dailyWeather.bottom, margin = 10.dp)
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