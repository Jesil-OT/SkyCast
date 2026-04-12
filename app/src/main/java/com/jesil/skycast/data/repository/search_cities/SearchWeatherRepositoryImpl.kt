package com.jesil.skycast.data.repository.search_cities

import com.jesil.skycast.data.mapper.toSearchCities
import com.jesil.skycast.data.model.SearchCities
import com.jesil.skycast.data.source.remote.SearchRemoteDataSource
import com.jesil.skycast.data.source.remote.model.SearchRemoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchWeatherRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource
): SearchWeatherRepository {

    override suspend fun searchCity(cityName: String): Flow<List<SearchCities>> {
        val searchedCity: Flow<List<SearchRemoteDto>> = searchRemoteDataSource.searchCity(
            cityName = cityName
        )
        return searchedCity.map { value -> value.toSearchCities() }
    }
}