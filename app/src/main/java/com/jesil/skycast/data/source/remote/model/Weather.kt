package com.jesil.skycast.data.source.remote.model

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)