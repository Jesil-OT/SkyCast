package com.jesil.skycast.features.cities.models

data class CityModel(
    val id: Int = 0,
    val location: String = "",
    val lat: Double? = 0.0,
    val lon: Double? = 0.0,
    val maxTemperature: String = "",
    val minTemperature: String = "",
    val weatherTypeIcon: String = "",
    val weatherType: String = "",
)
