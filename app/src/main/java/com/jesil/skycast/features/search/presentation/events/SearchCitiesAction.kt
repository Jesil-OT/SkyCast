package com.jesil.skycast.features.search.presentation.events

interface SearchCitiesAction {
    data class SearchCity(val cityName: String): SearchCitiesAction
    data class ClearCity(val cityName: String): SearchCitiesAction
}