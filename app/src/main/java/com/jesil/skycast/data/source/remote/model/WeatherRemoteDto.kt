package com.jesil.skycast.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherRemoteDto(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    @SerialName("coord")
    val coordinates: Coord,
    @SerialName("dt")
    val date: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)