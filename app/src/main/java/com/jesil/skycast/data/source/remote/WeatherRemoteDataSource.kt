package com.jesil.skycast.data.source.remote

import com.jesil.skycast.BuildConfig
import com.jesil.skycast.data.source.remote.model.WeatherListRemoteDto
import com.jesil.skycast.ui.util.Constants.APP_ID
import com.jesil.skycast.ui.util.Constants.LATITUDE
import com.jesil.skycast.ui.util.Constants.LONGITUDE
import com.jesil.skycast.ui.util.Constants.WEATHER_LIST_END_POINT
import com.jesil.skycast.ui.util.Response
import com.jesil.skycast.ui.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class WeatherRemoteDataSource(
    private val httpClient: HttpClient,
) {

    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ) =
        safeApiCall<WeatherListRemoteDto> {
            httpClient.get(
                urlString = BuildConfig.SERVER_URL + WEATHER_LIST_END_POINT
            ) {
                parameter(LATITUDE, latitude)
                parameter(LONGITUDE, longitude)
                parameter(APP_ID, BuildConfig.SERVER_API_KEY)
            }
        }.flowOn(Dispatchers.IO)
            .map { response ->
                when (response) {
                    is Response.Error -> throw Exception(response.errorMessage)
                    is Response.Success -> response.data
                }
            }

}