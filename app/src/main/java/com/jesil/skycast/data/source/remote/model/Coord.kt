package com.jesil.skycast.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    @SerialName("lat")
    val latitude: Double? = null,
    @SerialName("lon")
    val longitude: Double? = null
)