package com.jesil.skycast.data.source.local.model

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherEntity(
    val day: Int,
    @ColumnInfo("weather_type_icon") val weatherTypeIcon: String,
    @ColumnInfo("temperature") val temperature: Int,
    @ColumnInfo("min_temperature") val minTemperature: Int
)
