package com.jesil.skycast.di

import com.jesil.skycast.data.repository.CurrentWeatherRepoImpl
import com.jesil.skycast.data.repository.CurrentWeatherRepository
import com.jesil.skycast.data.source.remote.WeatherRemoteDataSource
import com.jesil.skycast.features.weather.presentation.WeatherViewModel
import com.jesil.skycast.ui.util.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create())}

    single { WeatherRemoteDataSource(get()) }

    singleOf(::CurrentWeatherRepoImpl) bind CurrentWeatherRepository::class

    viewModelOf(::WeatherViewModel) bind WeatherViewModel::class

}