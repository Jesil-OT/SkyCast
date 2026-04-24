package com.jesil.skycast.data.mapper

import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.source.local.model.CityWeatherEntity
import com.jesil.skycast.data.source.local.model.DailyWeatherEntity
import com.jesil.skycast.data.source.local.model.HourlyWeatherEntity
import com.jesil.skycast.features.cities.models.CityModel
import com.jesil.skycast.features.weather.models.DailyWeatherStateUi
import com.jesil.skycast.features.weather.models.HoursWeatherStateUi
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
        timeZone = timeZone.epochSecond.toInt(),
        latitude = latitude,
        longitude = longitude,
        hourlyWeather = hourlyWeather.map { it.toHourlyEntity() },
        dailyWeather = dailyWeather.map { it.toDailyEntity() }
    )
}
private fun CurrentWeather.toHourlyEntity(): HourlyWeatherEntity {
    return HourlyWeatherEntity(
        time = timeZone.epochSecond.toInt(),
        weatherTypeIcon = weatherTypeIcon,
        temperature = temperature,
        minTemperature = minTemperature
    )
}

private fun CurrentWeather.toDailyEntity(): DailyWeatherEntity {
    return DailyWeatherEntity(
        day = timeZone.epochSecond.toInt(),
        weatherTypeIcon = weatherTypeIcon,
        temperature = temperature,
        minTemperature = minTemperature
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
        latitude = latitude,
        longitude = longitude,
        hourlyWeather = hourlyWeather.map { it.toHourlyWeather() },
        dailyWeather = dailyWeather.map { it.toDailyWeather() }
    )
}

private fun HourlyWeatherEntity.toHourlyWeather(): CurrentWeather {
    return CurrentWeather(
        timeZone = Instant.ofEpochSecond(time.toLong()),
        weatherTypeIcon = weatherTypeIcon,
        temperature = temperature,
        minTemperature = minTemperature,
    )
}

private fun DailyWeatherEntity.toDailyWeather(): CurrentWeather {
    return CurrentWeather(
        timeZone = Instant.ofEpochSecond(day.toLong()),
        weatherTypeIcon = weatherTypeIcon,
        temperature = temperature,
        minTemperature = minTemperature,
    )
}


fun WeatherStateUi.fromCurrentWeatherUI(
    latitude: Double,
    longitude: Double
): CurrentWeather {
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
        timeZone = timeZone,
        latitude = latitude,
        longitude = longitude,
        hourlyWeather = hourlyWeather.map { it.fromHourlyWeatherUi()},
        dailyWeather = dailyWeather.map { it.fromDailyWeatherUi() }
    )
}

private fun HoursWeatherStateUi.fromHourlyWeatherUi(): CurrentWeather {
    return CurrentWeather(
        timeZone = time,
        weatherTypeIcon = weatherTypeIcon,
        temperature = temperature.trim {it == '°'}.toInt(),
        minTemperature = minTemperature.trim {it == '°'}.toInt()
    )
}

private fun DailyWeatherStateUi.fromDailyWeatherUi(): CurrentWeather{
    return CurrentWeather(
        timeZone = day,
        weatherTypeIcon = weatherTypeIcon,
        temperature = temperature.trim {it == '°'}.toInt(),
        minTemperature = minTemperature.trim {it == '°'}.toInt()
    )
}


fun CurrentWeather.toCityModel(): CityModel {
    return CityModel(
        id = id,
        location = location,
        maxTemperature = temperature.toString() + TEMP_CELSIUS,
        minTemperature = minTemperature.toString()  + TEMP_CELSIUS,
        weatherTypeIcon = weatherTypeIcon,
        weatherType = weatherType,
        lat = latitude,
        lon = longitude
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
        timeZone = timeZone,
        sunrise = sunrise,
        sunset = sunset,
        latitude = latitude,
        longitude = longitude,
        hourlyWeather = hourlyWeather.map { it.toHoursWeatherUI() },
        dailyWeather = dailyWeather.map { it.toDailyWeatherUI() }
    )
}
