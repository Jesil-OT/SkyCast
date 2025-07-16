package com.jesil.skycast.features.location.model

data class LocationState(
    val isPermissionGranted: Boolean = false,
    val error: String? = null,
)
