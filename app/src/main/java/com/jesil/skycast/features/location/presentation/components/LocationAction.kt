package com.jesil.skycast.features.location.presentation.components

sealed interface LocationAction {
    data object RequestPermission: LocationAction
}