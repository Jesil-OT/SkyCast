package com.jesil.skycast.features.search.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toCurrentWeatherUI
import com.jesil.skycast.data.repository.current_weather.CurrentWeatherRepository
import com.jesil.skycast.features.search.presentation.events.SearchWeatherActions
import com.jesil.skycast.features.weather.models.WeatherViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class SearchWeatherViewModel(
    private val currentWeatherRepository: CurrentWeatherRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(), KoinComponent {
    private val _searchWeatherState = MutableStateFlow<WeatherViewState>(WeatherViewState.Loading)
    val searchWeatherState = _searchWeatherState.asStateFlow()

    init {

        val lat: Double = (savedStateHandle.get<String>("lat") ?: 0f).toString().toDouble()
        val long: Double = (savedStateHandle.get<String>("long") ?: 0f).toString().toDouble()

        getCurrentWeatherFromCity(
            latitude = lat,
            longitude = long
        )
    }

    fun onAction(action: SearchWeatherActions) {
        when(action){
            is SearchWeatherActions.AddedCity -> {}
            is SearchWeatherActions.Retry -> {}
        }
    }

    private fun getCurrentWeatherFromCity(
        latitude: Double,
        longitude: Double
    ){
        viewModelScope.launch {
            currentWeatherRepository.fetchCurrentWeather(
                latitude = latitude,
                longitude = longitude
            ).onStart {
                _searchWeatherState.update { WeatherViewState.Loading }
            }.catch { err ->
                _searchWeatherState.update {  WeatherViewState.Error(err.message.toString()) }
            }.collect{ data ->
                _searchWeatherState.update { WeatherViewState.Success(data.toCurrentWeatherUI()) }
            }
        }
    }
}