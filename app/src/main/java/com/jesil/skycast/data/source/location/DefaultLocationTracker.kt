package com.jesil.skycast.data.source.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.jesil.skycast.data.source.location.model.Location
import com.jesil.skycast.ui.util.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

class DefaultLocationTracker(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Context
) : LocationTracker {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getCurrentLocation(): Response<Location> {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        if (!hasAccessFineLocationPermission || !hasCoarseLocationPermission || !isGpsEnabled) {
            return Response.Error("Missing location permission")
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(
                            Response.Success(Location(result.latitude, result.longitude)),
                            onCancellation = null
                        )
                    } else {
                        cont.resume(Response.Error("Failed to get location"), onCancellation = null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener { result ->
                    cont.resume(
                        Response.Success(Location(result.latitude, result.longitude)),
                        onCancellation = null
                    )
                }
                addOnFailureListener {
                    cont.resume(Response.Error("Failed to get location"), onCancellation = null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }
}