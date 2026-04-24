package com.jesil.skycast.data.mapper

import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.model.SearchCities
import com.jesil.skycast.data.source.local.model.CityWeatherEntity
import com.jesil.skycast.data.source.local.model.DailyWeatherEntity
import com.jesil.skycast.data.source.local.model.HourlyWeatherEntity
import com.jesil.skycast.data.source.remote.model.SearchRemoteDto
import com.jesil.skycast.data.source.remote.model.SingleWeather
import com.jesil.skycast.data.source.remote.model.WeatherListRemoteDto
import com.jesil.skycast.features.search.model.SearchCitiesStateUi
import com.jesil.skycast.features.weather.models.DailyWeatherStateUi
import com.jesil.skycast.features.weather.models.HoursWeatherStateUi
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.ui.util.Constants.HUMIDITY
import com.jesil.skycast.ui.util.Constants.PRESSURE
import com.jesil.skycast.ui.util.Constants.TEMP_CELSIUS
import com.jesil.skycast.ui.util.Constants.VISIBILITY_SPEED
import com.jesil.skycast.ui.util.Constants.WIND_SPEED
import com.jesil.skycast.ui.util.convertMToKM
import com.jesil.skycast.ui.util.convertMsToKhm
import com.jesil.skycast.ui.util.convertToCelsius
import com.jesil.skycast.ui.util.formatTimestamp
import com.jesil.skycast.ui.util.formatUnixDay
import com.jesil.skycast.ui.util.formatUnixTime
import com.jesil.skycast.ui.util.formatUnixTimeSimple
import java.time.Instant

fun WeatherListRemoteDto.toCurrentDailyWeather(): CurrentWeather {
    return CurrentWeather(
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
        sunrise = Instant.ofEpochSecond(city.sunrise.toString().toLong()),
        sunset = Instant.ofEpochSecond(city.sunset.toString().toLong()),
        pressure = currentWeatherList[0].main.pressure,
        seaLevel = currentWeatherList[0].main.seaLevel,
        minTemperature = currentWeatherList[0].main.minimumTemperature.convertToCelsius(),
        visibility = currentWeatherList[0].visibility?.convertMToKM(),
        hourlyWeather = currentWeatherList.map { it.toHoursWeather() }.take(9),
        dailyWeather = currentWeatherList.filterDailyEntries().map { it.toHoursWeather() }
    )
}

private fun List<SingleWeather>.filterDailyEntries(): List<SingleWeather> {
    val daysSeen = mutableSetOf<String>()
    return this.filter { forecast ->
        val dayKey = forecast.dateInText.take( 10) // Extract "YYYY-MM-DD" eg 2023-06-28
        daysSeen.add(dayKey) // Returns true if day wasn't in set
    }
}

fun List<SearchRemoteDto>.toSearchCities(): List<SearchCities> {
    return this.map { it.toSearchCities() }
}

fun SearchRemoteDto.toSearchCities(): SearchCities {
    return SearchCities(
        placeName = placeName,
        latitude = latitude,
        longitude = longitude,
        country = country,
        state = state
    )
}

fun SingleWeather.toHoursWeather(): CurrentWeather {
    return CurrentWeather(
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

fun CurrentWeather.toCurrentWeatherUI(): WeatherStateUi {
    return WeatherStateUi(
        id = id,
        location = "$location, $country",
        time = timeZone.formatTimestamp(),
        temperature = temperature.toString() + TEMP_CELSIUS,
        weatherType = weatherType,
        weatherTypeDescription = weatherTypeDescription,
        weatherTypeIcon = weatherTypeIcon,
        windSpeed = windSpeed.toString() + WIND_SPEED,
        humidity = humidity.toString() + HUMIDITY,
        rainChance = "0%",
        timeZone = timeZone,
        sunrise = sunrise,
        sunset = sunset,
        pressure = pressure.toString() + PRESSURE,
        seaLevel = seaLevel.toString() + PRESSURE,
        minTemperature = minTemperature.toString() + TEMP_CELSIUS,
        visibility = visibility.toString() + VISIBILITY_SPEED,
        hourlyWeather = hourlyWeather.map { it.toHoursWeatherUI() },
        dailyWeather = dailyWeather.map { it.toDailyWeatherUI() }
    )
}

fun CurrentWeather.toHoursWeatherUI(): HoursWeatherStateUi {
    return HoursWeatherStateUi(
        time = timeZone,
        weatherTypeIcon = weatherTypeIcon,
        temperature = temperature.toString() + TEMP_CELSIUS,
        minTemperature = minTemperature.toString() + TEMP_CELSIUS
    )
}

fun CurrentWeather.toDailyWeatherUI(): DailyWeatherStateUi {
//    val currentTime = if (timeZone == Instant.now()) "Today" else timeZone.formatUnixDay()
    return DailyWeatherStateUi(
        day = timeZone,
        weatherTypeIcon = weatherTypeIcon,
        temperature = temperature.toString() + TEMP_CELSIUS,
        minTemperature = minTemperature.toString() + TEMP_CELSIUS
    )
}

fun List<SearchCities>.toSearchCitiesUI(): List<SearchCitiesStateUi>{
    return this.map { it.toSearchCitiesUI() }
}

fun WeatherListRemoteDto.toCurrentWeatherEntity(): CityWeatherEntity {
    return CityWeatherEntity(
        id = city.id,
        location = city.name,
        country = city.country,
        temperature = currentWeatherList[0].main.temperature.convertToCelsius(),
        weatherType = currentWeatherList[0].weather[0].main,
        weatherTypeDescription = currentWeatherList[0].weather[0].description,
        weatherTypeIcon = currentWeatherList[0].weather[0].icon,
        windSpeed = currentWeatherList[0].wind.speed.convertMsToKhm(),
        humidity = currentWeatherList[0].main.humidity,
        timeZone = currentWeatherList[0].date,
        sunrise = city.sunrise,
        sunset = city.sunset,
        pressure = currentWeatherList[0].main.pressure,
        seaLevel = currentWeatherList[0].main.seaLevel,
        minTemperature = currentWeatherList[0].main.minimumTemperature.convertToCelsius(),
        visibility = currentWeatherList[0].visibility?.convertMToKM(),
        hourlyWeather = currentWeatherList.map { it.toHourlyWeatherEntity() }.take(9),
        dailyWeather = currentWeatherList.filterDailyEntries().map { it.toDailyWeatherEntity() },
        latitude = 0.0,
        longitude = 0.0
    )
}

private fun SingleWeather.toHourlyWeatherEntity(): HourlyWeatherEntity  {
    return HourlyWeatherEntity(
        time = date,
        weatherTypeIcon = weather[0].icon,
        temperature = main.temperature.convertToCelsius(),
        minTemperature = main.feelsLikeTemperature.convertToCelsius()
    )
}

private fun SingleWeather.toDailyWeatherEntity(): DailyWeatherEntity {
    return DailyWeatherEntity(
        day = date,
        weatherTypeIcon = weather[0].icon,
        temperature = main.temperature.convertToCelsius(),
        minTemperature = main.feelsLikeTemperature.convertToCelsius()
    )
}

fun SearchCities.toSearchCitiesUI(): SearchCitiesStateUi {
    return SearchCitiesStateUi(
        cityName = "$placeName $state, $country",
        latitude = latitude.toString(),
        longitude = longitude.toString()
    )
}