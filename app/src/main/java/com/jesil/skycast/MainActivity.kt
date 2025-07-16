package com.jesil.skycast

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesil.skycast.features.location.presentation.LocationPermissionScreen
import com.jesil.skycast.features.location.presentation.LocationViewModel
import com.jesil.skycast.features.weather.presentation.WeatherScreen
import com.jesil.skycast.features.weather.presentation.WeatherViewModel
import com.jesil.skycast.ui.theme.SkyCastTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var startDes = Screens.LocationScreen.route
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            startDes = if ( it[Manifest.permission.ACCESS_FINE_LOCATION] == true  && it[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                Screens.WeatherScreen.route
            } else {
                Screens.LocationScreen.route
            }

        }.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
        setContent {
            SkyCastTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = startDes
                ) {
                    composable(Screens.LocationScreen.route) {
                        LocationPermissionScreen(
                           onNext = {
                               navController.navigate(Screens.WeatherScreen.route){
                                   popUpTo(Screens.LocationScreen.route){
                                       inclusive = true
                                   }
                               }
                           }
                        )
                    }

                    composable(Screens.WeatherScreen.route) {
                        val weatherViewModel: WeatherViewModel = koinViewModel()
                        val weatherState by weatherViewModel.weatherViewState.collectAsStateWithLifecycle()

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