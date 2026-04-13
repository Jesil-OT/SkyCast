package com.jesil.skycast.features.search.presentation.events

interface SearchCitiesAction {
    data class SearchCity(val cityName: String): SearchCitiesAction
    data class SelectCity(val lat: Double, val long: Double): SearchCitiesAction
    data class ClearCity(val cityName: String): SearchCitiesAction
}