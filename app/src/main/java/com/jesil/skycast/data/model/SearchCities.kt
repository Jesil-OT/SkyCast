package com.jesil.skycast.data.model


data class SearchCities(
    val placeName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val country: String = "",
    val state: String = "",
)