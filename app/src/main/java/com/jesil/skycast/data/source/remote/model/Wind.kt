package com.jesil.skycast.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)