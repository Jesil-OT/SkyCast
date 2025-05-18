package com.jesil.skycast.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleWeather(
    val clouds: Clouds,
    @SerialName("dt")
    val date: Int,
    @SerialName("dt_txt")
    val dateInText: String,
    val main: Main,
    val pop: Double,
//    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)