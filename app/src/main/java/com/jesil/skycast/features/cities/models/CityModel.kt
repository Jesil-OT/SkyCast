package com.jesil.skycast.features.cities.models

data class CityModel(
    val location: String,
    val lat: Double,
    val lon: Double,
    val maxTemperature: String,
    val minTemperature: String,
    val weatherTypeIcon: String,
    val weatherType: String,
){
    val id = "$lat$lon"
}
