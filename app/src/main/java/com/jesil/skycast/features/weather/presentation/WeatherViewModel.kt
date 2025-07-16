package com.jesil.skycast.features.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toCurrentWeatherUI
import com.jesil.skycast.data.repository.CurrentWeatherRepository
import com.jesil.skycast.data.source.data_store.LocalDataStore
import com.jesil.skycast.features.weather.models.WeatherViewState
import com.jesil.skycast.features.weather.presentation.events.WeatherAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import timber.log.Timber

class WeatherViewModel(
    private val currentWeatherRepository: CurrentWeatherRepository,
    private val localDataStore: LocalDataStore
) : ViewModel(), KoinComponent {
    private val tag = "WeatherViewModel"

    private val _weatherViewState = MutableStateFlow<WeatherViewState>(WeatherViewState.Loading)
    val weatherViewState = _weatherViewState.asStateFlow()
    // Todo(get users current location)
//            getCurrentWeatherFromCurrentLocation(
//                latitude = 4.740843,
//                longitude = 7.036059
//            )
//            latitude = -30.621959,
//            longitude = 124.624402
    //for testing Port Harcourt
//            latitude = 4.740843,
//            longitude = 7.036059

    init { getCurrentWeatherFromCurrentLocation() }

    private fun getCurrentWeatherFromCurrentLocation() {
        viewModelScope.launch {
//            _weatherViewState.value = WeatherViewState.Idle
            val currentLatLong = localDataStore.getLocation().firstOrNull()
            currentWeatherRepository.fetchCurrentWeather(
                latitude = currentLatLong?.latitude ?: 0.0,
                longitude = currentLatLong?.longitude ?: 0.0
            ).onStart {
                _weatherViewState.value = WeatherViewState.Loading
            }.catch { err ->
                _weatherViewState.value = WeatherViewState.Error(err.message.toString())
            }.collect { data ->
                _weatherViewState.value = WeatherViewState.Success(data.toCurrentWeatherUI())
                Timber.tag(tag)
                    .d("getCurrentWeather: The current weather from data is $data ")
                Timber.tag(tag)
                    .d(("The current weather from UI is ${data.toCurrentWeatherUI()}"))
            }
        }
    }

    fun onAction(action: WeatherAction) {
        when (action) {
            is WeatherAction.Retry -> getCurrentWeatherFromCurrentLocation()

        }
    }
}