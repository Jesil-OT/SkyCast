package com.jesil.skycast.data.source.remote

import com.jesil.skycast.BuildConfig
import com.jesil.skycast.data.source.remote.model.SearchRemoteDto
import com.jesil.skycast.ui.util.Constants
import com.jesil.skycast.ui.util.Constants.APP_ID
import com.jesil.skycast.ui.util.Response
import com.jesil.skycast.ui.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SearchRemoteDataSource(
    private val httpClient: HttpClient,
) {

    suspend fun searchCity(
        cityName: String
    ) = safeApiCall<List<SearchRemoteDto>> {
        httpClient.get(
            urlString = Constants.BASE_URL + Constants.SEARCH_END_POINT
        ){
            parameter(Constants.CITY_NAME, cityName)
            parameter(Constants.LIMIT, 10)
            parameter(APP_ID, BuildConfig.APP_ID)
        }
    }.flowOn(Dispatchers.IO)
        .map { response ->
            when(response){
                is Response.Error -> throw Exception(response.errorMessage)
                is Response.Success -> response.data
            }
        }
}