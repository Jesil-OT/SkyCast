package com.jesil.skycast.data.repository.search_cities

import com.jesil.skycast.data.mapper.toCurrentWeather
import com.jesil.skycast.data.mapper.toSearchCities
import com.jesil.skycast.data.mapper.toWeatherEntity
import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.model.SearchCities
import com.jesil.skycast.data.source.local.CityWeatherDao
import com.jesil.skycast.data.source.local.model.CityWeatherEntity
import com.jesil.skycast.data.source.remote.SearchRemoteDataSource
import com.jesil.skycast.data.source.remote.model.SearchRemoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchWeatherRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchLocalDataSource: CityWeatherDao
): SearchWeatherRepository {

    override suspend fun searchCity(cityName: String): Flow<List<SearchCities>> {
        val searchedCity: Flow<List<SearchRemoteDto>> = searchRemoteDataSource.searchCity(
            cityName = cityName
        )
        return searchedCity.map { value -> value.toSearchCities() }
    }

    override suspend fun addCity(city: CurrentWeather) {
        searchLocalDataSource.insertCityWeather(city.toWeatherEntity())
    }
}
