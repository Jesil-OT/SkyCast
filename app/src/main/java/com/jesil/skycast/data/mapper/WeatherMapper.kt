package com.jesil.skycast.data.mapper

import com.jesil.skycast.data.model.CurrentDailyWeather
import com.jesil.skycast.data.model.HoursWeather
import com.jesil.skycast.data.source.remote.model.HourlyRemoteDto
import com.jesil.skycast.data.source.remote.model.WeatherRemoteDto
import com.jesil.skycast.features.weather.models.WeatherDataUi
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.ui.util.Constants.HUMIDITY
import com.jesil.skycast.ui.util.Constants.PRESSURE
import com.jesil.skycast.ui.util.Constants.TEMP_CELSIUS
import com.jesil.skycast.ui.util.Constants.WIND_SPEED
import com.jesil.skycast.ui.util.convertMsToKhm
import com.jesil.skycast.ui.util.convertToCelsius
import com.jesil.skycast.ui.util.formatTimestamp
import com.jesil.skycast.ui.util.formatUnixTime
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
    )
}

fun List<WeatherRemoteDto>.toCurrentHourlyWeather(): List<HoursWeather>{
    return listOf(
        HoursWeather(
            time = Instant.ofEpochSecond(this[0].date.toLong()),
            temperature = this[0].main.temp.convertToCelsius(),
            weatherTypeIcon = this[0].weather[0].icon,
            minTemperature = this[0].main.tempMin.convertToCelsius()
        )
    )
}

fun CurrentDailyWeather.toCurrentWeatherUI() : WeatherDataUi{
    return WeatherDataUi(
        location = location,
        time = timeZone.formatTimestamp(),
        temperature = temperature.toString() + TEMP_CELSIUS,
        weatherType = weatherType,
        weatherTypeDescription = weatherTypeDescription,
        weatherTypeIcon = weatherTypeIcon,
        windSpeed = windSpeed.toString() + WIND_SPEED,
        humidity = humidity.toString() + HUMIDITY,
        rainChance = "0%",
        timeZone = timeZone.formatTimestamp(),
        sunrise = sunrise.formatUnixTime(),
        sunset = sunset.formatUnixTime(),
        pressure = pressure.toString() + PRESSURE,
        minTemperature = minTemperature.toString() + TEMP_CELSIUS,
//        hourlyWeather = hourlyWeather.
    )
}