package com.jesil.skycast.features.search.model

data class SearchCitiesStateUi(
    val cityName: String = "",
    val latitude: String = "0.0",
    val longitude: String = "0.0",
)

sealed class SearchCitiesViewState{
    object EmptyState: SearchCitiesViewState()
    data class Success(val data: List<SearchCitiesStateUi>): SearchCitiesViewState()
}