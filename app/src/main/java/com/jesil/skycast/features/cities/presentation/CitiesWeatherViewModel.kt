package com.jesil.skycast.features.cities.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toWeatherStateUi
import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.repository.cities.CitiesRepository
import com.jesil.skycast.features.weather.models.WeatherStateUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class CitiesWeatherViewModel(
    private val citiesWeatherRepository: CitiesRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(), KoinComponent {

    private val _cityWeather = MutableStateFlow(WeatherStateUi())
    val cityWeather = _cityWeather.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = WeatherStateUi()
    )

    init {
        val id: Int = (savedStateHandle.get<Int>("id") ?: 0)
        getCityWeather(id)
    }
    private fun getCityWeather(id: Int) {
        viewModelScope.launch {
            citiesWeatherRepository.getCityWeather(id).collect { city ->
                if (city != null) {
                    _cityWeather.value = city.toWeatherStateUi()
                }
            }
        }
    }
}