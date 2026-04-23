package com.jesil.skycast.features.cities.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jesil.skycast.R
import com.jesil.skycast.core.navigation.Screens
import com.jesil.skycast.features.cities.models.CityModel
import com.jesil.skycast.features.cities.presentation.components.SearchBarDisplay
import com.jesil.skycast.features.cities.presentation.components.WeatherCityItem
import com.jesil.skycast.features.cities.presentation.events.CitiesAction
import com.jesil.skycast.ui.theme.SkyCastTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesScreen(
    state: List<CityModel>,
    selectedCities: Set<String>,
    onActions: (CitiesAction) -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                title = {}
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = selectedCities.isNotEmpty(),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 70.dp),
                        contentAlignment = Alignment.Center,
                        content = {
                            Column(
                                modifier = Modifier.clickable(
                                    onClick = { onActions(CitiesAction.DeleteCity) }
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                content = {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = stringResource(R.string.delete),
                                        tint = MaterialTheme.colorScheme.onBackground,
                                    )
                                    Text(
                                        text = stringResource(R.string.delete),
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            )
                        }
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
            ) {
                Text(
                    modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                    text = "Manage Cities",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 27.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                SearchBarDisplay(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    onClick = { navController.navigate(Screens.SearchCitiesScreen.route) }
                )
                AnimatedContent(
                    targetState = state.isEmpty(),
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.92f))
                            .togetherWith(fadeOut(animationSpec = tween(400)))
                    },
                    content = { noCities ->
                        if (noCities) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 20.dp),
                                        text = stringResource(R.string.no_city_found),
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp),
                                content = {
                                    items(
                                        items = state,
                                        key = { it.id }
                                    ) { city ->
                                        WeatherCityItem(
                                            modifier = Modifier.animateItem(),
                                            item = city,
                                            isSelected = selectedCities.contains(city.id.toString()),
                                            onClick = { id -> navController.navigate(Screens.CitiesWeatherScreen.route + "/$id" + "/${city.lat}" + "/${city.lon}") },
                                            onLongPress = { isPressed ->
                                                onActions(
                                                    CitiesAction.OnLongPress(
                                                        city.id.toString(),
                                                        isPressed
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    }
                )
            }
        }
    )
}


@PreviewLightDark
@Composable
private fun CitiesScreenPreview() {
    SkyCastTheme {
        CitiesScreen(
            state = fakeWeatherList,
            selectedCities = emptySet(),
            onActions = {},
            navController = rememberNavController()
        )
    }
}

internal val fakeWeatherList = listOf<CityModel>(
    CityModel(
        id = 0,
        location = "Lagos, NG",
        lat = 6.45,
        lon = 9.68,
        maxTemperature = "26°",
        minTemperature = "29°",
        weatherTypeIcon = "01n",
        weatherType = "Clear sky"
    ),
    CityModel(
        id = 1,
        location = "London, UK",
        lat = 51.50,
        lon = -0.12,
        maxTemperature = "31°",
        minTemperature = "29°",
        weatherTypeIcon = "03d",
        weatherType = "Clouds"
    ),
    CityModel(
        id = 2,
        location = "Paris, FR",
        lat = 48.85,
        lon = 2.39,
        maxTemperature = "35°",
        minTemperature = "29°",
        weatherTypeIcon = "02d",
        weatherType = "Scattered clouds"
    ),
    CityModel(
        id = 3,
        location = "New York, US",
        lat = 40.7,
        lon = -74.0,
        maxTemperature = "30°",
        minTemperature = "29°",
        weatherTypeIcon = "01d",
        weatherType = "Clear sky"
    )
)