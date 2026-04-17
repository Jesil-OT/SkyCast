package com.jesil.skycast.data.repository.cities

import com.jesil.skycast.data.model.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getAllCities(): Flow<List<CurrentWeather>>
}