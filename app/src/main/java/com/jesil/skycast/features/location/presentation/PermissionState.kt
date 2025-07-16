package com.jesil.skycast.features.location.presentation


sealed class PermissionButtonState{
    object Request: PermissionButtonState()
    object Allow: PermissionButtonState()
}