package com.jesil.skycast.data.source.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.jesil.skycast.data.source.location.model.Location
import com.jesil.skycast.ui.util.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

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
//                if (isComplete) {
//                    if (isSuccessful) {
//                        cont.resume(
//                            Response.Success(Location(result.latitude, result.longitude)),
//                            onCancellation = null
//                        )
//                    } else {
//                        cont.resume(Response.Error("Failed to get location"), onCancellation = null)
//                    }
//                    return@suspendCancellableCoroutine
//                }
                if (!isComplete) {
                    return@suspendCancellableCoroutine
                }
                if (isSuccessful){
                    cont.resume(
                        Response.Success(Location(result.latitude, result.longitude)),
                        onCancellation = null
                    )
                } else {
                    cont.resume(Response.Error("Failed to get location"), onCancellation = null)
                }
                addOnSuccessListener { location ->
                    if (location != null) {
                        cont.resume(
                            Response.Success(Location(location.latitude, location.longitude)),
                            onCancellation = null
                        )
                    }else {
                        requestNewLocationData{ lat, lon ->
                            cont.resume(
                                Response.Success(Location(lat, lon)),
                                onCancellation = null
                            )
                        }
                    }
                }
                addOnFailureListener {
                    cont.resume(Response.Error("Failed to get location ${it.message}"), onCancellation = null)
                }
                addOnCanceledListener { cont.cancel() }
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun requestNewLocationData(
        value: (lat: Double, lon: Double) -> Unit
    ){
        // 1. Define the parameters for the request
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .setMaxUpdates(1) // We only want one fresh fix to stop the null crash
            .build()

        // 2. Define a callback to handle the result
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val lastLocation = locationResult.lastLocation
                if (lastLocation != null) {
                    // Success! You now have a non-null location
                    val lat = lastLocation.latitude
                    val lon = lastLocation.longitude
                    value.invoke(lat, lon)
                    Timber.d("Fresh Location: $lat, $lon")

                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }
}