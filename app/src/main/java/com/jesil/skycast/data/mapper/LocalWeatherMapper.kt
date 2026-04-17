package com.jesil.skycast.data.mapper

import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.source.local.model.CityWeatherEntity
import com.jesil.skycast.features.cities.models.CityModel
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.ui.util.Constants.TEMP_CELSIUS
import java.time.Instant

fun CurrentWeather.toWeatherEntity(): CityWeatherEntity{
    return CityWeatherEntity(
        id = id,
        location = location,
        country = country,
        temperature = temperature,
        weatherType = weatherType,
        weatherTypeDescription = weatherTypeDescription,
        weatherTypeIcon = weatherTypeIcon,
        windSpeed = windSpeed,
        humidity = humidity,
        sunrise = sunrise.epochSecond.toInt(),
        sunset = sunset.epochSecond.toInt(),
        pressure = pressure,
        minTemperature = minTemperature,
        timeZone = 3600,
//        hourlyWeather = emptyList(),
//        dailyWeather = emptyList()
    )
}

fun List<CityWeatherEntity>.toCurrentWeather(): List<CurrentWeather> {
    return this.map { it.toCurrentWeatherSingle() }
}

private fun CityWeatherEntity.toCurrentWeatherSingle(): CurrentWeather {
    return CurrentWeather(
        id = id,
        location = location,
        country = country,
        temperature = temperature,
        weatherType = weatherType,
        weatherTypeDescription = weatherTypeDescription,
        weatherTypeIcon = weatherTypeIcon,
        windSpeed = windSpeed,
        humidity = humidity,
        sunrise = Instant.ofEpochSecond(sunrise.toLong()),
        sunset = Instant.ofEpochSecond(sunset.toLong()),
        pressure = pressure,
        minTemperature = minTemperature,
        timeZone = Instant.ofEpochSecond(timeZone.toLong()),
        hourlyWeather = emptyList(),
        dailyWeather = emptyList()
    )
}

fun WeatherStateUi.fromCurrentWeatherUI(): CurrentWeather {
    val value = " km/h"
    return CurrentWeather(
        id = id,
        location = location,
        country = "",
        temperature = temperature.trim { c -> c == '°' }.toInt(),
        weatherType = weatherType,
        weatherTypeDescription = weatherTypeDescription,
        weatherTypeIcon = weatherTypeIcon,
        windSpeed = windSpeed.removeSuffix(value).toInt(),
        humidity = humidity.trim { c -> c == '%' }.toInt(),
        sunrise = Instant.now(),
        sunset = Instant.now(),
        pressure = pressure.removeSuffix(" hPa").toInt(),
        minTemperature = minTemperature.trim { c -> c == '°' }.toInt(),
        timeZone = Instant.now(),
        hourlyWeather = emptyList(),
        dailyWeather = emptyList()
    )
}

fun CurrentWeather.toCityModel(): CityModel {
    return CityModel(
        id = id,
        location = location,
        maxTemperature = temperature.toString() + TEMP_CELSIUS,
        minTemperature = minTemperature.toString()  + TEMP_CELSIUS,
        weatherTypeIcon = weatherTypeIcon,
        weatherType = weatherType
    )

}


//fun HourlyWeather.toHourlyEntity(): CityWeatherEntity {
//    // map hourly fields into CityWeatherEntity
//}
//
//fun DailyWeather.toDailyEntity(): CityWeatherEntity {
//    // map daily fields into CityWeatherEntity
//}
