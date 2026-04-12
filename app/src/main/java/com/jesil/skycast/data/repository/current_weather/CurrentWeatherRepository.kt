package com.jesil.skycast.data.repository.current_weather

import com.jesil.skycast.data.model.CurrentDailyWeather
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherRepository {

    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<CurrentDailyWeather>

}