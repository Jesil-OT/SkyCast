package com.jesil.skycast.features.location

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.source.location.LocationTracker
import com.jesil.skycast.features.location.presentation.components.LocationAction
import com.jesil.skycast.ui.util.Response
import kotlinx.coroutines.launch

class LocationViewModel(
    private val locationTracker: LocationTracker
): ViewModel() {

    val isPermissionGranted = mutableStateOf(false)

    fun onAction(action: LocationAction) {
        when(action) {
            is LocationAction.RequestPermission -> getCurrentLocation()
        }
    }

    private fun getCurrentLocation() = viewModelScope.launch {
        locationTracker.getCurrentLocation().let { location ->
//            isPermissionGranted.value = location is Response.Success
            when(location) {
                is Response.Error -> {
                    //TODO: Handle error

                }
                is Response.Success -> {
                    //TODO: Handle success by saving it to data store
                    isPermissionGranted.value = true
                }
            }
        }
    }
}