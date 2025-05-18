package com.jesil.skycast.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Main(
    @SerialName("feels_like") val feelsLikeTemperature: Double,
    @SerialName("grnd_level") val groundLevel: Int,
    val humidity: Int,
    val pressure: Int,
    @SerialName("sea_level") val seaLevel: Int,
    @SerialName("temp") val temperature: Double,
    @SerialName("temp_kf") val temperatureInKf: Double,
    @SerialName("temp_max") val maximumTemperature: Double,
    @SerialName("temp_min") val minimumTemperature: Double
)