package com.jesil.skycast.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(
    val route: String
) {
    @Serializable
    data object LocationScreen : Screens(route = "location_screen")
    @Serializable
    data object WeatherScreen : Screens(route = "weather_screen")
    @Serializable
    data object CitiesScreen : Screens(route = "cities_screen")

    @Serializable
    data object SearchCitiesScreen : Screens(route = "search_cities_screen")

}