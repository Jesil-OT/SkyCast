package com.jesil.skycast.data.repository

import com.jesil.skycast.data.mapper.toCurrentDailyWeather
import com.jesil.skycast.data.model.CurrentDailyWeather
import com.jesil.skycast.data.source.remote.WeatherRemoteDataSource
import com.jesil.skycast.data.source.remote.model.WeatherListRemoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrentWeatherRepoImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : CurrentWeatherRepository {

    override suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<CurrentDailyWeather> {
        val currentWeather: Flow<WeatherListRemoteDto> = weatherRemoteDataSource.fetchCurrentWeather(
            latitude = latitude,
            longitude = longitude
        )

        return currentWeather.map { value ->
            value.toCurrentDailyWeather()
        }
    }


}