package com.jesil.skycast.data.source.remote

import com.jesil.skycast.BuildConfig
import com.jesil.skycast.data.source.remote.model.HourlyRemoteDto
import com.jesil.skycast.data.source.remote.model.WeatherRemoteDto
import com.jesil.skycast.ui.util.Response
import com.jesil.skycast.ui.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WeatherRemoteDataSource(
    private val httpClient: HttpClient,
//    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Response<WeatherRemoteDto>> =
        safeApiCall {
            httpClient.get(
                urlString = BuildConfig.SERVER_URL + "weather"
            ) {
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("appid", BuildConfig.SERVER_API_KEY)
            }
        }

    suspend fun fetchCurrentHourlyWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Response<HourlyRemoteDto>> =
//        withContext(ioDispatcher) {
        safeApiCall {
            httpClient.get(
                urlString = BuildConfig.SERVER_URL + "forecast"
            ) {
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("appid", BuildConfig.SERVER_API_KEY)
            }
//        }
        }

}