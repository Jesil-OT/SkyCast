package com.jesil.skycast.features.location

sealed interface LocationAction {
    data object RequestPermission: LocationAction
    data object NavigateToWeather: LocationAction
}