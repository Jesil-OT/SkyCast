package com.jesil.skycast.data.mapper

import com.jesil.skycast.data.model.CurrentDailyWeather
import com.jesil.skycast.data.source.remote.model.SingleWeather
import com.jesil.skycast.data.source.remote.model.Weather
import com.jesil.skycast.data.source.remote.model.WeatherListRemoteDto
import com.jesil.skycast.features.weather.models.HoursWeatherStateUi
import com.jesil.skycast.features.weather.models.WeatherDataUi
import com.jesil.skycast.ui.util.Constants.HUMIDITY
import com.jesil.skycast.ui.util.Constants.PRESSURE
import com.jesil.skycast.ui.util.Constants.TEMP_C
import com.jesil.skycast.ui.util.Constants.TEMP_CELSIUS
import com.jesil.skycast.ui.util.Constants.WIND_SPEED
import com.jesil.skycast.ui.util.convertMsToKhm
import com.jesil.skycast.ui.util.convertToCelsius
import com.jesil.skycast.ui.util.formatTimestamp
import com.jesil.skycast.ui.util.formatUnixTime
import com.jesil.skycast.ui.util.formatUnixTimeSimple
import java.time.Instant

fun WeatherListRemoteDto.toCurrentDailyWeather(): CurrentDailyWeather {
    return CurrentDailyWeather(
        id = city.id,
        location = city.name,
        country = city.country,
        temperature = currentWeatherList[0].main.temperature.convertToCelsius(),
        weatherType = currentWeatherList[0].weather[0].main,
        weatherTypeDescription = currentWeatherList[0].weather[0].description,
        weatherTypeIcon = currentWeatherList[0].weather[0].icon,
        windSpeed = currentWeatherList[0].wind.speed.convertMsToKhm(),
        humidity = currentWeatherList[0].main.humidity,
        timeZone = Instant.ofEpochSecond(currentWeatherList[0].date.toLong()),
        sunrise = Instant.ofEpochSecond(city.sunrise.toLong()),
        sunset = Instant.ofEpochSecond(city.sunset.toLong()),
        pressure = currentWeatherList[0].main.pressure,
        minTemperature = currentWeatherList[0].main.minimumTemperature.convertToCelsius(),
        hourlyWeather = currentWeatherList.map { it.toHoursWeather() }.take(9)
    )
}

fun SingleWeather.toHoursWeather(): CurrentDailyWeather {
    return CurrentDailyWeather(
        id = 0,
        location = "",
        country = "",
        temperature = main.temperature.convertToCelsius(),
        weatherType = weather[0].main,
        weatherTypeDescription = weather[0].description,
        weatherTypeIcon = weather[0].icon,
        windSpeed = wind.speed.convertMsToKhm(),
        humidity = main.humidity,
        timeZone =  Instant.ofEpochSecond(date.toLong()),
        sunrise = Instant.now(),
        sunset = Instant.now(),
        pressure = main.pressure,
        minTemperature = main.feelsLikeTemperature.convertToCelsius(),
        hourlyWeather = emptyList()
    )
}

fun CurrentDailyWeather.toCurrentWeatherUI(): WeatherDataUi {
    return WeatherDataUi(
        location = "$location, $country",
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
        minTemperature = minTemperature.toString() + TEMP_CELSIUS + TEMP_C,
        hourlyWeather = hourlyWeather.map { it.toHoursWeatherUI() }
    )
}

fun CurrentDailyWeather.toHoursWeatherUI(): HoursWeatherStateUi {
    return HoursWeatherStateUi(
        time = timeZone.formatUnixTimeSimple(),
        weatherTypeIcon = weatherTypeIcon,
        temperature = temperature.toString() + TEMP_CELSIUS,
        minTemperature = minTemperature.toString() + TEMP_CELSIUS
    )
}