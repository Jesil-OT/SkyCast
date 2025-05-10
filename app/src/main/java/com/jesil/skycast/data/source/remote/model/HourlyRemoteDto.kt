package com.jesil.skycast.data.source.remote.model

import kotlinx.serialization.SerialName

data class HourlyRemoteDto(
    @SerialName("cod")
    val code: Int,
    @SerialName("cnt")
    val count: Int,
    val message: Int,
    @SerialName("list")
    val hourlyData: List<WeatherRemoteDto>

)