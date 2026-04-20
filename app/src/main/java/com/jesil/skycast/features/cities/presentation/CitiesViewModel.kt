package com.jesil.skycast.features.cities.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toCityModel
import com.jesil.skycast.data.model.CurrentWeather
import com.jesil.skycast.data.repository.cities.CitiesRepository
import com.jesil.skycast.features.cities.models.CityModel
import com.jesil.skycast.features.cities.presentation.events.CitiesAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class CitiesViewModel(
    private val citiesRepository: CitiesRepository
): ViewModel() {

    private val _state = MutableStateFlow<List<CityModel>>(emptyList())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    private val _selectedCities = MutableStateFlow<Set<String>>(emptySet())
    val selectedCities: StateFlow<Set<String>> = _selectedCities

    init {
        viewModelScope.launch {
            citiesRepository.getAllCities().collect{ cities ->
                _state.update { cities.map { it.toCityModel() } }
            }
        }
    }
    fun onAction(action: CitiesAction){
        when(action){
            is CitiesAction.NavigateTo -> {

            }
            is CitiesAction.OnLongPress -> {
              _selectedCities.update { city ->
                  if(action.isPressed) city.add(action.id) else city.remove(action.id)
              }
            }
            is CitiesAction.DeleteCity -> {
                deleteCity(_selectedCities.value.toList().map { it.toInt() })
                _selectedCities.update { emptySet() }
            }
        }
    }

    private fun deleteCity(ids: List<Int>){
        viewModelScope.launch {
            citiesRepository.deleteCity(ids = ids)
        }
    }

}
private fun Set<String>.add(id: String): Set<String>  = this.plus(id)

private fun Set<String>.remove(id: String): Set<String>  = this.minus(id)
