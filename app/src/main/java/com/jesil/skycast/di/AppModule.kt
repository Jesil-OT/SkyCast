package com.jesil.skycast.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jesil.skycast.data.repository.CurrentWeatherRepoImpl
import com.jesil.skycast.data.repository.CurrentWeatherRepository
import com.jesil.skycast.data.source.location.DefaultLocationTracker
import com.jesil.skycast.data.source.location.LocationTracker
import com.jesil.skycast.data.source.remote.WeatherRemoteDataSource
import com.jesil.skycast.features.weather.presentation.WeatherViewModel
import com.jesil.skycast.ui.util.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create())}

    single { WeatherRemoteDataSource(get()) }

    singleOf(::CurrentWeatherRepoImpl) bind CurrentWeatherRepository::class

    viewModelOf(::WeatherViewModel) bind WeatherViewModel::class

}

val locationModule =  module {
    single<Application>(named("appContext")) { androidContext() as Application }
    single<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(get()) }

    singleOf(::DefaultLocationTracker) bind LocationTracker::class


}