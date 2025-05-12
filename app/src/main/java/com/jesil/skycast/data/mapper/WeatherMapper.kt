package com.jesil.skycast.data.mapper

import com.jesil.skycast.data.model.CurrentDailyWeather
import com.jesil.skycast.data.model.HoursWeather
import com.jesil.skycast.data.source.remote.model.HourlyRemoteDto
import com.jesil.skycast.data.source.remote.model.WeatherRemoteDto
import com.jesil.skycast.ui.util.convertMsToKhm
import com.jesil.skycast.ui.util.convertToCelsius
import java.time.Instant

fun WeatherRemoteDto.toCurrentDailyWeather(): CurrentDailyWeather{
    return CurrentDailyWeather(
        id = id,
        location = name,
        temperature = main.temp.convertToCelsius(),
        weatherType = weather[0].main,
        weatherTypeDescription = weather[0].description,
        weatherTypeIcon = weather[0].icon,
        windSpeed = wind.speed.convertMsToKhm(),
        humidity = main.humidity,
        timeZone = Instant.ofEpochSecond(timezone.toLong()),
        sunrise = Instant.ofEpochSecond(sys.sunrise.toLong()),
        sunset = Instant.ofEpochSecond(sys.sunset.toLong()),
        pressure = main.pressure,
        minTemperature = main.tempMin.convertToCelsius(),
//        hourlyWeather = hourlyData.toCurrentHourlyWeather()
    )
}

fun List<HourlyRemoteDto>.toCurrentHourlyWeather(): List<HoursWeather>{
    return listOf(
        HoursWeather(
            time = Instant.ofEpochSecond(this[0].hourlyData[0].date.toLong()),
            temperature = this[0].hourlyData[0].main.temp.convertToCelsius(),
            weatherTypeIcon = this[0].hourlyData[0].weather[0].icon,
            minTemperature = this[0].hourlyData[0].main.tempMin.convertToCelsius()
        )
    )
}