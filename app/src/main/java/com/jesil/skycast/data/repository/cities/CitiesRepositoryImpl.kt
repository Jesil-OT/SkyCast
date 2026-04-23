package com.jesil.skycast.data.repository.cities

import com.jesil.skycast.data.mapper.toCurrentDailyWeather
import com.jesil.skycast.data.mapper.toCurrentWeather
import com.jesil.skycast.data.mapper.toCurrentWeatherEntity
import com.jesil.skycast.data.mapper.toCurrentWeatherSingle
import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.source.local.CityWeatherDao
import com.jesil.skycast.data.source.local.model.CityWeatherEntity
import com.jesil.skycast.data.source.remote.WeatherRemoteDataSource
import com.jesil.skycast.data.source.remote.model.WeatherListRemoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
class CitiesRepositoryImpl(
    private val searchLocalDataSource: CityWeatherDao,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
): CitiesRepository {

    override fun getAllCities(): Flow<List<CurrentWeather>> {
        val allCities: Flow<List<CityWeatherEntity>> = searchLocalDataSource.getAllCitiesWeather()
        return allCities.map { it.toCurrentWeather() }
    }

    override fun getCityWeather(id: Int): Flow<CurrentWeather?> {
        val cityWeather: Flow<CityWeatherEntity?> = searchLocalDataSource.getCityWeather(id)
        return cityWeather.map { value -> value?.toCurrentWeatherSingle() }
    }

    override suspend fun deleteCity(ids: List<Int>) =
        searchLocalDataSource.deleteItemsByIds(ids)

    override suspend fun refreshCityWeather(
        latitude: Double,
        longitude: Double,
        onErrorMessage: suspend (String) -> Unit
    ) {

        val currentCityWeather = weatherRemoteDataSource.fetchCurrentWeather(
            latitude = latitude,
            longitude = longitude
        )
        currentCityWeather
            .catch { err -> onErrorMessage(err.message.toString()) }
            .collect { value ->
                searchLocalDataSource.updateCityWeather(
                    cityWeatherEntity = value.toCurrentWeatherEntity()
                )
            }
    }

}
