package com.jesil.skycast

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesil.skycast.features.location.LocationPermissionScreen
import com.jesil.skycast.features.location.LocationViewModel
import com.jesil.skycast.features.weather.presentation.WeatherScreen
import com.jesil.skycast.features.weather.presentation.WeatherViewModel
import com.jesil.skycast.ui.theme.SkyCastTheme
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyCastTheme {
                val navController = rememberNavController()
                val weatherViewModel: WeatherViewModel = koinViewModel()
                val locationViewModel: LocationViewModel = koinViewModel()
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
