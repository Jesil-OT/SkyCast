package com.jesil.skycast

sealed class Screens(
    val route: String,
) {
    data object LocationScreen : Screens(route = "location_screen")
    data object WeatherScreen : Screens(route = "weather_screen")

}