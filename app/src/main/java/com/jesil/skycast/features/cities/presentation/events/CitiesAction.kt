package com.jesil.skycast.features.cities.presentation.events

sealed interface CitiesAction {
    data class OnLongPress(val id: String, val isPressed: Boolean): CitiesAction
    data object DeleteCity: CitiesAction
}