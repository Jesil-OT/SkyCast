package com.jesil.skycast.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(
    val route: String
){
    @Serializable
    data object LocationScreen : Screens(route = "location_screen")
    @Serializable
    data object WeatherScreen : Screens(route = "weather_screen")
    @Serializable
    data object CitiesScreen : Screens(route = "cities_screen")
    @Serializable
    data object SearchCitiesScreen : Screens(route = "search_cities_screen")
    @Serializable
    data object SearchWeatherScreen : Screens(route = "search_weather_screen")
    @Serializable
    data object CitiesWeatherScreen : Screens(route = "cities_weather_screen")

}