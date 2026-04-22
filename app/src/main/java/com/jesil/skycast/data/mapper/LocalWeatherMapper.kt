package com.jesil.skycast.data.mapper

import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.source.local.model.CityWeatherEntity
import com.jesil.skycast.features.cities.models.CityModel
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.ui.util.Constants.HUMIDITY
import com.jesil.skycast.ui.util.Constants.PRESSURE
import com.jesil.skycast.ui.util.Constants.TEMP_CELSIUS
import com.jesil.skycast.ui.util.Constants.VISIBILITY_SPEED
import com.jesil.skycast.ui.util.Constants.WIND_SPEED
import com.jesil.skycast.ui.util.formatTimestamp
import com.jesil.skycast.ui.util.formatUnixTime
import java.time.Instant
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
        visibility = visibility,
        seaLevel = seaLevel,
        timeZone = 3600,
//        hourlyWeather = emptyList(),
//        dailyWeather = emptyList()
    )
}

fun List<CityWeatherEntity>.toCurrentWeather(): List<CurrentWeather> {
    return this.map { it.toCurrentWeatherSingle() }
}

fun CityWeatherEntity.toCurrentWeatherSingle(): CurrentWeather {
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
        visibility = visibility,
        seaLevel = seaLevel,
        hourlyWeather = emptyList(),
        dailyWeather = emptyList()
    )
}

fun WeatherStateUi.fromCurrentWeatherUI(): CurrentWeather {
    return CurrentWeather(
        id = id,
        location = location,
        country = "",
        temperature = temperature.trim { c -> c == '°' }.toInt(),
        weatherType = weatherType,
        weatherTypeDescription = weatherTypeDescription,
        weatherTypeIcon = weatherTypeIcon,
        windSpeed = windSpeed.removeSuffix(" km/h").toInt(),
        humidity = humidity.trim { c -> c == '%' }.toInt(),
        sunrise = sunrise,
        sunset = sunset,
        pressure = pressure.removeSuffix(" hPa").toInt(),
        minTemperature = minTemperature.trim { c -> c == '°' }.toInt(),
        visibility = visibility.removeSuffix(" km").toInt(),
        seaLevel = pressure.removeSuffix(" hPa").toInt(),
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

fun CurrentWeather.toWeatherStateUi(): WeatherStateUi {
    return WeatherStateUi(
        id = id,
        location = location,
        temperature = temperature.toString() + TEMP_CELSIUS,
        weatherType = weatherType,
        weatherTypeDescription = weatherTypeDescription,
        weatherTypeIcon = weatherTypeIcon,
        windSpeed = windSpeed.toString() + WIND_SPEED,
        humidity = humidity.toString() + HUMIDITY,
        pressure = pressure.toString() + PRESSURE,
        seaLevel = seaLevel.toString() + PRESSURE,
        visibility = visibility.toString() + VISIBILITY_SPEED,
        minTemperature = minTemperature.toString() + TEMP_CELSIUS,
        timeZone = timeZone.formatTimestamp(),
        sunrise = sunrise,
        sunset = sunset,
        hourlyWeather = emptyList(),
        dailyWeather = emptyList()
    )
}


//fun HourlyWeather.toHourlyEntity(): CityWeatherEntity {
//    // map hourly fields into CityWeatherEntity
//}
//
//fun DailyWeather.toDailyEntity(): CityWeatherEntity {
//    // map daily fields into CityWeatherEntity
//}
