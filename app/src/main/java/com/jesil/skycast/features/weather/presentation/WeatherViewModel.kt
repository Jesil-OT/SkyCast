package com.jesil.skycast.features.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toCurrentWeatherUI
import com.jesil.skycast.data.repository.CurrentWeatherRepository
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.features.weather.models.WeatherViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherViewModel(
    private val currentWeatherRepository: CurrentWeatherRepository
) : ViewModel() {
    private val TAG = "WeatherViewModel"

    init {
        Timber.tag(TAG).d("WeatherViewModel: init")
    }

    private val _weatherState = MutableStateFlow(WeatherStateUi())
    val weatherState =
        _weatherState.onStart {
            getCurrentWeather(
                latitude = 4.740843,
                longitude = 7.036059
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WeatherStateUi()
        )

    private val _weatherViewState = MutableStateFlow<WeatherViewState>(WeatherViewState.Idle)
    val weatherViewState = _weatherViewState
        .onStart {
            getCurrentWeatherFromCurrentLocation(
                latitude = 4.740843,
                longitude = 7.036059
            )
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
            }
        }
    }

    private fun getCurrentWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            currentWeatherRepository.fetchCurrentWeather(
                latitude = latitude,
                longitude = longitude
            ).onStart {
                _weatherState.update {
                    it.copy(isLoading = true)
                }
            }.catch { err ->
                _weatherState.update {
                    it.copy(
                        isLoading = false,
                        error = err.message
                    )
                }
            }.collect { data ->
                _weatherState.update {
                    it.copy(
                        isLoading = false,
                        weatherData = data.toCurrentWeatherUI()
                    )
                }
                Timber.tag(TAG)
                    .d("getCurrentWeather: The current weather from data is $data ")
                Timber.tag(TAG)
                    .d(("The current weather from UI is ${data.toCurrentWeatherUI()}"))
            }
        }
    }
}