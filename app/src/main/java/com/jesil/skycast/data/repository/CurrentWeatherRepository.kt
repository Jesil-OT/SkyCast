package com.jesil.skycast.data.repository

import com.jesil.skycast.data.model.CurrentDailyWeather
import com.jesil.skycast.ui.util.Response
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherRepository {
    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Response<CurrentDailyWeather>>
}