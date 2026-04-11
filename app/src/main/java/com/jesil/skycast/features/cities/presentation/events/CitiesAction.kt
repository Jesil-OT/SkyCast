package com.jesil.skycast.features.cities.presentation.events

sealed interface CitiesAction {
    data class NavigateTo(val lat: Double, val lng: Double): CitiesAction
    data class OnLongPress(val id: String, val isPressed: Boolean): CitiesAction
}