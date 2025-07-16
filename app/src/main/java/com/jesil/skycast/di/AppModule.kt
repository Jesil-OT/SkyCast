package com.jesil.skycast.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jesil.skycast.data.repository.CurrentWeatherRepoImpl
import com.jesil.skycast.data.repository.CurrentWeatherRepository
import com.jesil.skycast.data.source.data_store.LocalDataStore
import com.jesil.skycast.data.source.data_store.LocalDataStoreImpl
import com.jesil.skycast.data.source.location.DefaultLocationTracker
import com.jesil.skycast.data.source.location.LocationTracker
import com.jesil.skycast.data.source.remote.WeatherRemoteDataSource
import com.jesil.skycast.features.location.presentation.LocationViewModel
import com.jesil.skycast.features.weather.presentation.WeatherViewModel
import com.jesil.skycast.ui.util.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create())}

//    single { androidContext() }

    single<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(androidContext()) } //FusedLocationProviderClient

    single { WeatherRemoteDataSource(get()) }

    single<LocalDataStore>{ LocalDataStoreImpl(androidContext()) }

    singleOf(::CurrentWeatherRepoImpl) bind CurrentWeatherRepository::class

    viewModel{ WeatherViewModel(get(), get()) }

    single<LocationTracker>{ DefaultLocationTracker(get(), androidContext()) }

    viewModel { LocationViewModel(get(), get()) }
}