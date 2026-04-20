package com.jesil.skycast.data.repository.cities

import com.jesil.skycast.data.mapper.toCurrentWeather
import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.source.local.CityWeatherDao
import com.jesil.skycast.data.source.local.model.CityWeatherEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CitiesRepositoryImpl(
    private val searchLocalDataSource: CityWeatherDao
): CitiesRepository {

    override fun getAllCities(): Flow<List<CurrentWeather>> {
        val allCities: Flow<List<CityWeatherEntity>> = searchLocalDataSource.getAllCitiesWeather()
        return allCities.map { it.toCurrentWeather() }
    }

    override suspend fun deleteCity(ids: List<Int>) =
        searchLocalDataSource.deleteItemsByIds(ids)

}