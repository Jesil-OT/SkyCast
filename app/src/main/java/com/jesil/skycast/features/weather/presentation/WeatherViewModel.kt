package com.jesil.skycast.features.weather.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toCurrentWeatherUI
import com.jesil.skycast.data.repository.CurrentWeatherRepository
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.ui.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val currentWeatherRepository: CurrentWeatherRepository
): ViewModel() {

    init {
        println("InIT")
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

    private fun getCurrentWeather(latitude: Double, longitude: Double){
        viewModelScope.launch {
//            _weatherState.update {
//                it.copy(isLoading = true)
//            }

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
                Log.d("WeatherViewModel", "getCurrentWeather: The current weather from data is $data ")
                Log.d("WeatherViewModel", ("The current weather from UI is ${data.toCurrentWeatherUI()}"))
            }
        }
    }
}