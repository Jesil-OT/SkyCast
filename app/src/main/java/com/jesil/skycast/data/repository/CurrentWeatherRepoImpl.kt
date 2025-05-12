package com.jesil.skycast.data.repository

import com.jesil.skycast.data.mapper.toCurrentDailyWeather
import com.jesil.skycast.data.model.CurrentDailyWeather
import com.jesil.skycast.data.source.remote.WeatherRemoteDataSource
import com.jesil.skycast.ui.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CurrentWeatherRepoImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
): CurrentWeatherRepository {
    // wrong code
    override suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Response<CurrentDailyWeather>> {
       return weatherRemoteDataSource.fetchCurrentWeather(latitude, longitude).flowOn(Dispatchers.IO).map {
           when(it){
               is Response.Error -> Response.Error(it.message)
               is Response.Loading -> Response.Loading
               is Response.Success -> Response.Success(it.data.toCurrentDailyWeather())
           }
       }
    }
}