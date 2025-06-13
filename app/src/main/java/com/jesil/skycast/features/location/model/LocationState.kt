package com.jesil.skycast.features.location.model

data class LocationState(
    var isPermissionGranted: Boolean = false,
    val error: String? = null,
)
