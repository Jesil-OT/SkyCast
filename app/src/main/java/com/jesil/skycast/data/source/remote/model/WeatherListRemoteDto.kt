package com.jesil.skycast.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherListRemoteDto(
    val city: City,
    @SerialName("cnt")
    val count: Int,
    @SerialName("cod")
    val code: String,
    @SerialName("list")
    val currentWeatherList: List<SingleWeather>,
    val message: Int
)