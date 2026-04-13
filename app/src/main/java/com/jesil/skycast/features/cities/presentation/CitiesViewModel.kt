package com.jesil.skycast.features.cities.presentation

import androidx.lifecycle.ViewModel
import com.jesil.skycast.features.cities.models.CityModel
import com.jesil.skycast.features.cities.presentation.events.CitiesAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.emptyList

class CitiesViewModel: ViewModel() {

    private val _state = MutableStateFlow(fakeWeatherList)
    val state = _state.asStateFlow()

    private val _selectedCities = MutableStateFlow<Set<String>>(emptySet())
    val selectedCities: StateFlow<Set<String>> = _selectedCities

    fun onAction(action: CitiesAction){
        when(action){
            is CitiesAction.NavigateTo -> {

            }
            is CitiesAction.OnLongPress -> {
              _selectedCities.update { city ->
                  if(action.isPressed) city.add(action.id) else city.remove(action.id)
              }
            }
        }
    }
}
private fun Set<String>.add(id: String): Set<String>  = this.plus(id)

private fun Set<String>.remove(id: String): Set<String>  = this.minus(id)
