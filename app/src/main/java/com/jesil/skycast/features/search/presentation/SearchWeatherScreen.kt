package com.jesil.skycast.features.search.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jesil.skycast.R
import com.jesil.skycast.core.navigation.Screens
import com.jesil.skycast.data.mapper.fromCurrentWeatherUI
import com.jesil.skycast.features.search.presentation.components.ActionButtons
import com.jesil.skycast.features.search.presentation.events.SearchWeatherActions
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.features.weather.models.WeatherViewState
import com.jesil.skycast.features.weather.presentation.components.DailyWeatherItem
import com.jesil.skycast.features.weather.presentation.components.HoursWeatherItem
import com.jesil.skycast.features.weather.presentation.components.LazyListHeader
import com.jesil.skycast.features.weather.presentation.components.Location
import com.jesil.skycast.features.weather.presentation.components.LottieAnimationPreloader
import com.jesil.skycast.features.weather.presentation.components.WeatherInfos
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.formatUnixDay
import com.jesil.skycast.ui.util.formatUnixTimeSimple
import com.jesil.skycast.ui.util.generateBackgroundColor
import com.jesil.skycast.ui.util.generateIcon
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWeatherScreen(
    state: WeatherViewState,
    onActions: (SearchWeatherActions) -> Unit,
    navController: NavController,
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.92f))
                        .togetherWith(fadeOut(animationSpec = tween(400)))
                },
                label = "search_weather_state"
            ) {
                when(it){
                    is WeatherViewState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            content = {
                                LottieAnimationPreloader(
                                    modifier = Modifier.size(30.dp),
                                    lottieId = R.raw.error
                                )
                                Text(
                                    text = it.message,
                                    modifier = Modifier.padding(top = 10.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                    }
                    is WeatherViewState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                            content = {
                                CircularProgressIndicator(
                                    strokeWidth = 10.dp
                                )
                            }
                        )
                    }
                    is WeatherViewState.Success -> {
                        SearchWeatherInnerScreen(
                            state = it.data,
                            modifier = Modifier.padding(paddingValues),
                            onCancel = {
                                navController.popBackStack()
                            },
                            onAddCity = {
                                onActions(SearchWeatherActions.AddedCity(it.data.fromCurrentWeatherUI()))
                                navController.navigate(Screens.CitiesScreen.route) {
                                    popUpTo(Screens.CitiesScreen.route){
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWeatherInnerScreen(
    modifier: Modifier = Modifier,
    state: WeatherStateUi,
    onCancel: () -> Unit = {},
    onAddCity: () -> Unit = {}
) {
    val backgroundColor = state.weatherTypeIcon.generateBackgroundColor()
    val backgroundColorStateTop by animateColorAsState(
        targetValue = backgroundColor[0],
        label = "background_color_top"
    )
    val backgroundColorStateBottom by animateColorAsState(
        targetValue = backgroundColor[1],
        label = "background_color_bottom"
    )
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        backgroundColorStateTop,
                        backgroundColorStateBottom
                    )
                )
            ) // background color for main content
            .verticalScroll(state = rememberScrollState())
    ) {
        val (actionButtons ,location, weatherType, temperature, weatherTypeDescription, feelLike,
            hourlyWeather, dailyWeather, weatherInfo) = createRefs()
        val daysCount = state.dailyWeather.size.toString()

        ActionButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(actionButtons) {
                    top.linkTo(parent.top, margin = 20.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                },
            onCancel = onCancel,
            onCityAdded = onAddCity
        )

        Location(
            location = state.location,
            modifier = Modifier
                .constrainAs(location) {
                    top.linkTo(actionButtons.bottom, margin = 5.dp)
                    start.linkTo(parent.start, margin = 25.dp)
                    end.linkTo(parent.end, margin = 25.dp)
                }
        )
        Image(
            painter = painterResource(state.weatherTypeIcon.generateIcon()),
            contentDescription = state.weatherType,
            modifier = Modifier
                .offset(y = (-40).dp, x = 100.dp)
                .size(270.dp)
                .constrainAs(weatherType) {
                    top.linkTo(location.bottom)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = state.temperature,
            style = MaterialTheme.typography.displaySmall,
            color = androidx.compose.ui.graphics.Color.White,
            fontSize = 70.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(y = (-10).dp)
                .constrainAs(temperature) {
                    top.linkTo(weatherType.top)
                    bottom.linkTo(weatherType.bottom)
                    start.linkTo(parent.start, margin = 25.dp)
                }
        )

        Text(
            text = state.weatherTypeDescription,
            style = MaterialTheme.typography.displayLarge,
            color = androidx.compose.ui.graphics.Color.White,
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(weatherTypeDescription) {
                    bottom.linkTo(temperature.bottom)
                    start.linkTo(parent.start, margin = 25.dp)
                    top.linkTo(temperature.bottom, margin = 25.dp)
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
                .constrainAs(feelLike) {
                    top.linkTo(temperature.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 25.dp)
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
                                color = androidx.compose.ui.graphics.Color.White.copy(.5f),
                                fontSize = 13.sp,
                            )
                        )
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    thickness = 1.dp,
                    color = Color.White.copy(.2f)
                )
                Spacer(modifier = Modifier.height(5.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items(state.hourlyWeather) { hourlyWeather ->
                        HoursWeatherItem(
                            time = hourlyWeather.time.formatUnixTimeSimple(),
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
                                    color = Color.White.copy(
                                        .5f
                                    ),
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
                        day = dailyWeather.day.formatUnixDay(),
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