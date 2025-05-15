package com.jesil.skycast.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val country: String,
    val sunrise: Int,
    val sunset: Int
)