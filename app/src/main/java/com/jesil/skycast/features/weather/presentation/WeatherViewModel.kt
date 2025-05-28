package com.jesil.skycast.features.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toCurrentWeatherUI
import com.jesil.skycast.data.repository.CurrentWeatherRepository
import com.jesil.skycast.features.weather.models.WeatherViewState
import com.jesil.skycast.features.weather.presentation.events.WeatherAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherViewModel(
    private val currentWeatherRepository: CurrentWeatherRepository
) : ViewModel() {
    private val tag = "WeatherViewModel"

    init {
        Timber.tag(tag).d("WeatherViewModel: init")
    }

    private val _weatherViewState = MutableStateFlow<WeatherViewState>(WeatherViewState.Idle)
    val weatherViewState = _weatherViewState
        .onStart {
            // Todo(get users current location)
            getCurrentWeatherFromCurrentLocation(
                latitude = 4.740843,
                longitude = 7.036059
            )
//            latitude = -30.621959,
//            longitude = 124.624402

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WeatherViewState.Idle
        )

    private fun getCurrentWeatherFromCurrentLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _weatherViewState.value = WeatherViewState.Idle
            currentWeatherRepository.fetchCurrentWeather(
                latitude = latitude,
                longitude = longitude
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
            is WeatherAction.Retry -> getCurrentWeatherFromCurrentLocation(
                latitude = 4.740843,
                longitude = 7.036059
            )
        }
    }
}