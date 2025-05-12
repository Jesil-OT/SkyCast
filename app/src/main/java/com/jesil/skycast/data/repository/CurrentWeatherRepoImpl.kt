package com.jesil.skycast.data.repository

import com.jesil.skycast.data.mapper.toCurrentDailyWeather
import com.jesil.skycast.data.mapper.toCurrentHourlyWeather
import com.jesil.skycast.data.model.CurrentDailyWeather
import com.jesil.skycast.data.source.remote.WeatherRemoteDataSource
import com.jesil.skycast.ui.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CurrentWeatherRepoImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : CurrentWeatherRepository {

    override suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Response<CurrentDailyWeather>> = flow<Response<CurrentDailyWeather>> {
        val currentWeatherResponse = weatherRemoteDataSource.fetchCurrentWeather(
            latitude = latitude,
            longitude = longitude
        ).catch {
            emit(Response.Error(it.message.toString()))
        }.map {
            when (it) {
                is Response.Error -> Response.Error(it.message)
                is Response.Loading -> Response.Loading
                is Response.Success -> Response.Success(it.data.toCurrentDailyWeather())
            }
        }

        val hourlyWeatherResponse = weatherRemoteDataSource.fetchCurrentHourlyWeather(
            latitude = latitude,
            longitude = longitude
        ).catch {
            emit(Response.Error(it.message.toString()))
        }.map {
            when (it) {
                is Response.Error -> Response.Error(it.message)
                is Response.Loading -> Response.Loading
                is Response.Success -> Response.Success(it.data.hourlyData.toCurrentHourlyWeather())
            }
        }



        combine(currentWeatherResponse, hourlyWeatherResponse) { current, hourly ->
            when {
                current is Response.Success && hourly is Response.Success -> {
                    Response.Success(
                        CurrentDailyWeather(
                            id = current.data.id,
                            location = current.data.location,
                            temperature = current.data.temperature,
                            weatherType = current.data.weatherType,
                            weatherTypeDescription = current.data.weatherTypeDescription,
                            weatherTypeIcon = current.data.weatherTypeIcon,
                            windSpeed = current.data.windSpeed,
                            humidity = current.data.humidity,
                            timeZone = current.data.timeZone,
                            sunrise = current.data.sunrise,
                            sunset = current.data.sunset,
                            pressure = current.data.pressure,
                            minTemperature = current.data.minTemperature,
                            hourlyWeather = hourly.data
                        )
                    )
                }

                current is Response.Error -> Response.Error(current.message)
                hourly is Response.Error -> Response.Error(hourly.message)
                else -> Response.Loading
            }
        }
    }.flowOn(Dispatchers.IO)
}