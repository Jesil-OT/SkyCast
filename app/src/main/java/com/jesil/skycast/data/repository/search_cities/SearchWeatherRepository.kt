package com.jesil.skycast.data.repository.search_cities

import com.jesil.skycast.data.model.SearchCities
import kotlinx.coroutines.flow.Flow

interface SearchWeatherRepository {

    suspend fun searchCity(cityName: String): Flow<List<SearchCities>>
}