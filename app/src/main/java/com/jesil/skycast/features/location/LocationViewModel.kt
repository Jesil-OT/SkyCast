package com.jesil.skycast.features.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.source.data_store.LocalDataStore
import com.jesil.skycast.data.source.location.LocationTracker
import com.jesil.skycast.features.location.model.LocationState
import com.jesil.skycast.features.location.presentation.components.LocationAction
import com.jesil.skycast.ui.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationViewModel(
    private val locationTracker: LocationTracker,
    private val localDataStore: LocalDataStore
) : ViewModel() {

    private val _locationState = MutableStateFlow(LocationState())
    val locationState = _locationState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = LocationState()
    )


    fun onAction(action: LocationAction) {
        when (action) {
            is LocationAction.RequestPermission -> getCurrentLocation()
        }
    }

    private fun getCurrentLocation() = viewModelScope.launch {
        locationTracker.getCurrentLocation().let { location ->
//            isPermissionGranted.value = location is Response.Success
            when (location) {
                is Response.Error -> {
                    //TODO: Handle error
                    _locationState.update {
                        it.copy(
                            isPermissionGranted = false,
                            error = location.errorMessage,
                        )
                    }
                }

                is Response.Success -> {
                    _locationState.update {
                        it.copy(
                            isPermissionGranted = true,
                            error = null,
                        )
                    }
                    localDataStore.saveLocation(location.data)
                }
            }
        }
    }
}