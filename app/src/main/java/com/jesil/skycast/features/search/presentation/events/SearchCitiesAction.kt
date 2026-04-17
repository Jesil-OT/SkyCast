package com.jesil.skycast.features.search.presentation.events

import com.jesil.skycast.data.model.CurrentWeather

interface SearchCitiesAction {
    data class SearchCity(val cityName: String): SearchCitiesAction
    data class ClearCity(val cityName: String): SearchCitiesAction
}