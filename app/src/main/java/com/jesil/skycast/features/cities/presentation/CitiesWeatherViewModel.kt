package com.jesil.skycast.features.cities.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toWeatherStateUi
import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.repository.cities.CitiesRepository
import com.jesil.skycast.features.weather.models.WeatherStateUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import timber.log.Timber

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

    val errorMessage : MutableSharedFlow<String> = MutableSharedFlow()

    init {
        val id: Int = (savedStateHandle.get<Int>("id") ?: 0)
        val lat: Double = (savedStateHandle.get<String>("cities_lat") ?: 0f).toString().toDouble()
        val long: Double = (savedStateHandle.get<String>("cities_long") ?: 0f).toString().toDouble()

        getCityWeather(id)
        refreshWeather(latitude = lat, longitude = long)
    }
    private fun getCityWeather(id: Int) {
        viewModelScope.launch {
            citiesWeatherRepository.getCityWeather(id).collect { city ->
                if (city != null) {
                    _cityWeather.value = city.toWeatherStateUi()
                    Timber.d("getting weather from local DB with current city name: ${city.location}")
                    Timber.d("getting weather from local DB with latitude: ${city.latitude}, longitude: ${city.longitude}")
                }
            }
        }
    }

    private fun refreshWeather(
        latitude: Double,
        longitude: Double
    ){
        viewModelScope.launch {
            Timber.d("Refreshing weather from network with current city latitude: $latitude, longitude: $longitude")
            citiesWeatherRepository.refreshCityWeather(
                latitude = latitude,
                longitude = longitude,
                onErrorMessage = {
                    errorMessage.emit(it)
                    Timber.e("Error message is $it")
                }
            )
        }
    }
}