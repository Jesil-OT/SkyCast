package com.jesil.skycast.features.search.presentation.events

import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.features.weather.models.WeatherStateUi

sealed interface SearchWeatherActions {
    data class AddedCity(val cityWeather: CurrentWeather): SearchWeatherActions
}