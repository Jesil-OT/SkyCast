package com.jesil.skycast

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jesil.skycast.core.navigation.Screens
import com.jesil.skycast.features.cities.presentation.CitiesScreen
import com.jesil.skycast.features.cities.presentation.CitiesViewModel
import com.jesil.skycast.features.location.presentation.LocationPermissionScreen
import com.jesil.skycast.features.location.presentation.LocationViewModel
import com.jesil.skycast.features.search.presentation.SearchCitiesScreen
import com.jesil.skycast.features.search.presentation.SearchCitiesViewModel
import com.jesil.skycast.features.search.presentation.SearchWeatherScreen
import com.jesil.skycast.features.search.presentation.SearchWeatherViewModel
import com.jesil.skycast.features.weather.presentation.WeatherScreen
import com.jesil.skycast.features.weather.presentation.WeatherViewModel
import com.jesil.skycast.ui.theme.SkyCastTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyCastTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                NavHost(
                    navController = navController,
                    startDestination = if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Screens.WeatherScreen.route
                    } else {
                        Screens.LocationScreen.route
                    }
                ) {
                    composable(Screens.LocationScreen.route) {
                        LocationPermissionScreen(
                            onNext = {
                                navController.navigate(Screens.WeatherScreen.route) {
                                    popUpTo(Screens.LocationScreen.route) {
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
                            navController = navController
                        )
                    }

                    composable(Screens.CitiesScreen.route) {
                        val citiesViewModel: CitiesViewModel = koinViewModel()
                        val citiesState by citiesViewModel.state.collectAsStateWithLifecycle()
                        val selectedCities by citiesViewModel.selectedCities.collectAsStateWithLifecycle()

                        CitiesScreen(
                            state = citiesState,
                            onActions = citiesViewModel::onAction,
                            selectedCities = selectedCities,
                            navController = navController
                        )
                    }

                    composable(Screens.SearchCitiesScreen.route) {
                        val searchCitiesViewModel: SearchCitiesViewModel = koinViewModel()
                        val searchState by searchCitiesViewModel.searchCitiesViewState.collectAsStateWithLifecycle()
                        val searchKeyword by searchCitiesViewModel.searchKeyword.collectAsStateWithLifecycle()

                        LaunchedEffect(searchCitiesViewModel.error){
                            searchCitiesViewModel.error.collect{ err ->
                                Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
                            }
                        }
                        SearchCitiesScreen(
                            state = searchState,
                            onActions = searchCitiesViewModel::onAction,
                            searchKeyword = searchKeyword,
                            navController = navController
                        )
                    }

                    composable(
                        route = "${Screens.SearchWeatherScreen.route}/{lat}/{long}",
                        arguments = listOf(
                            navArgument("lat") { type = NavType.StringType },
                            navArgument("long") { type = NavType.StringType }
                        )
                    ) {
                        val searchWeatherViewModel: SearchWeatherViewModel = koinViewModel()
                        val searchWeatherState by searchWeatherViewModel.searchWeatherState.collectAsStateWithLifecycle()

                        SearchWeatherScreen(
                            state = searchWeatherState,
                            onActions = searchWeatherViewModel::onAction,
                            navController = navController
                        )

                    }
                }
            }
        }
    }
}