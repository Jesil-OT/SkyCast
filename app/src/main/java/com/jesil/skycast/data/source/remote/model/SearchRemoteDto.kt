package com.jesil.skycast.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRemoteDto(
    @SerialName("name")
    val placeName: String,
    @SerialName("lat")
    val latitude: Double,
    @SerialName("lon")
    val longitude: Double,
    val country: String,
    val state: String? = null,
)