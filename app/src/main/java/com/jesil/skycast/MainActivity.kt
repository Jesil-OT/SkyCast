package com.jesil.skycast

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesil.skycast.features.location.LocationPermissionScreen
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
                val state by weatherViewModel.weatherViewState.collectAsStateWithLifecycle()

                NavHost(
                    navController = navController,
                    startDestination = Screens.LocationScreen.route
                ) {
                    composable(Screens.LocationScreen.route) {
                        LocationPermissionScreen(
                            isPermissionGranted = true,
                            navController = navController,
                            onAction = {}
                        )
                    }

                    composable(Screens.WeatherScreen.route) {
                        WeatherScreen(
                            state = state,
                            onActions = weatherViewModel::onAction,
                        )
                    }
                }
            }
        }
    }
}
