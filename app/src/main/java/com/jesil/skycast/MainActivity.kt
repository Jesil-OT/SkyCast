package com.jesil.skycast

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesil.skycast.features.location.LocationPermissionScreen
import com.jesil.skycast.features.location.LocationViewModel
import com.jesil.skycast.features.location.presentation.components.LocationAction
import com.jesil.skycast.features.weather.presentation.WeatherScreen
import com.jesil.skycast.features.weather.presentation.WeatherViewModel
import com.jesil.skycast.ui.theme.SkyCastTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.text.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    val weatherViewModel: WeatherViewModel by viewModels()
    val locationViewModel: LocationViewModel by viewModels()
//val weatherViewModel: WeatherViewModel = getViewModel<WeatherViewModel>()
//    val locationViewModel: LocationViewModel = getViewModel<LocationViewModel>()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            locationViewModel.locationState.value.isPermissionGranted = it[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
        enableEdgeToEdge()
        setContent {
            SkyCastTheme {
                val navController = rememberNavController()
                val weatherViewModel: WeatherViewModel = getViewModel<WeatherViewModel>()
                val locationViewModel: LocationViewModel = getViewModel<LocationViewModel>()
                val weatherState by weatherViewModel.weatherViewState.collectAsStateWithLifecycle()
                val locationState by locationViewModel.locationState.collectAsStateWithLifecycle()

                val configureStateDes = if (locationState.isPermissionGranted) Screens.WeatherScreen.route else Screens.LocationScreen.route

                NavHost(
                    navController = navController,
                    startDestination = configureStateDes
                ) {
                    composable(Screens.LocationScreen.route) {
                        LocationPermissionScreen(
                            isPermissionGranted = locationState.isPermissionGranted,
                            navController = navController,
                            onAction = locationViewModel::onAction
                        )
                    }

                    composable(Screens.WeatherScreen.route) {
                        WeatherScreen(
                            state = weatherState,
                            onActions = weatherViewModel::onAction,
                        )
                    }
                }
            }
        }
    }
}
