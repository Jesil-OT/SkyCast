package com.jesil.skycast.features.weather.presentation.events


sealed interface WeatherAction {
    data object Retry: WeatherAction
}