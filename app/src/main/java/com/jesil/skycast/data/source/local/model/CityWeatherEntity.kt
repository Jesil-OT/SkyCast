package com.jesil.skycast.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.jesil.skycast.data.model.CurrentWeather
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(tableName = "cities_weather_table")
@Serializable
data class CityWeatherEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val location: String,
    val country: String,
    val temperature: Int,
    @ColumnInfo(name = "weather_type") val weatherType: String,
    @ColumnInfo(name = "weather_type_description") val weatherTypeDescription: String,
    @ColumnInfo(name = "weather_type_icon") val weatherTypeIcon: String,
    @ColumnInfo(name = "wind_speed") val windSpeed: Int,
    val humidity: Int,
    val sunrise: Int,
    val sunset: Int,
    val pressure: Int,
    val visibility: Int?,
    val seaLevel: Int,
    @ColumnInfo(name = "min_temperature") val minTemperature: Int,
    @ColumnInfo(name = "time_zone") val timeZone: Int,
    @ColumnInfo(name = "hourly_weather") val hourlyWeather: List<HourlyWeatherEntity>,
    @ColumnInfo(name = "daily_weather") val dailyWeather: List<DailyWeatherEntity>

)

class Converters {
    @TypeConverter
    fun fromHourlyWeatherList(value: List<HourlyWeatherEntity>?): String {
        return if (value == null) "" else Json.encodeToString(value)    }

    @TypeConverter
    fun toHourlyWeatherList(value: String): List<HourlyWeatherEntity>? {
        return if (value.isEmpty()) emptyList() else Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromDailyWeatherList(value: List<DailyWeatherEntity>?): String {
        return if (value == null) "" else Json.encodeToString(value)
    }

    @TypeConverter
    fun toDailyWeatherList(value: String): List<DailyWeatherEntity>? {
        return if (value.isEmpty()) emptyList() else Json.decodeFromString(value)
    }
}
