package com.jesil.skycast.features.weather.presentation.components


sealed interface WeatherAction {
    data object Retry: WeatherAction
}