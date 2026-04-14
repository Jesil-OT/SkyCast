package com.jesil.skycast.features.search.presentation.events

sealed interface SearchWeatherActions {
    object AddedCity: SearchWeatherActions
    object Retry: SearchWeatherActions
}