package com.jesil.skycast.di

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jesil.skycast.data.repository.current_weather.CurrentWeatherRepoImpl
import com.jesil.skycast.data.repository.current_weather.CurrentWeatherRepository
import com.jesil.skycast.data.repository.search_cities.SearchWeatherRepository
import com.jesil.skycast.data.repository.search_cities.SearchWeatherRepositoryImpl
import com.jesil.skycast.data.source.data_store.LocalDataStore
import com.jesil.skycast.data.source.data_store.LocalDataStoreImpl
import com.jesil.skycast.data.source.location.DefaultLocationTracker
import com.jesil.skycast.data.source.location.LocationTracker
import com.jesil.skycast.data.source.remote.SearchRemoteDataSource
import com.jesil.skycast.data.source.remote.WeatherRemoteDataSource
import com.jesil.skycast.features.cities.presentation.CitiesViewModel
import com.jesil.skycast.features.location.presentation.LocationViewModel
import com.jesil.skycast.features.search.presentation.SearchCitiesViewModel
import com.jesil.skycast.features.weather.presentation.WeatherViewModel
import com.jesil.skycast.ui.util.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> { HttpClientFactory.create(CIO.create())}

    single<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(androidContext()) } //FusedLocationProviderClient

    single { WeatherRemoteDataSource(get()) }

    single { SearchRemoteDataSource(get()) }

    single<LocalDataStore>{ LocalDataStoreImpl(androidContext()) }

    singleOf(::CurrentWeatherRepoImpl) bind CurrentWeatherRepository::class

    singleOf(::SearchWeatherRepositoryImpl) bind SearchWeatherRepository::class

    viewModelOf(::WeatherViewModel)

    single<LocationTracker>{ DefaultLocationTracker(get(), androidContext()) }

    viewModelOf(::LocationViewModel)

    viewModelOf(::CitiesViewModel)

    viewModelOf(::SearchCitiesViewModel)
}