package com.jesil.skycast.features.location.presentation

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jesil.skycast.R
import com.jesil.skycast.Screens
import com.jesil.skycast.features.location.presentation.components.LocationButton
import com.jesil.skycast.ui.theme.SkyCastTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LocationPermissionScreen(
    onNext: () -> Unit,
) {
    val locationViewModel: LocationViewModel = koinViewModel()
    var isPermissionGranted by remember { mutableStateOf(false) }
    
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val arePermissionsGranted = permissions.values.reduce { acc, next -> acc && next }
        isPermissionGranted = if (arePermissionsGranted) {
            locationViewModel.getCurrentLocation()
            true
        } else {
            false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(id = R.drawable.location_image),
            contentDescription = stringResource(R.string.location_permission_image),
        )
        Text(
            text = stringResource(R.string.location_permission_text),
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(horizontal = 30.dp),
            text = stringResource(R.string.location_permission_description),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(50.dp))
        if (!isPermissionGranted) {
            LocationButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                onClick = {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = stringResource(R.string.allow),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.allow),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 15.sp
                        )
                    )
                }
            )
        } else {
            LocationButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                onClick = onNext,
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                        contentDescription = stringResource(R.string.next),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.next),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 15.sp
                        )
                    )
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LocationPermissionScreenPreview() {
    SkyCastTheme {
        LocationPermissionScreen(
//            navController = rememberNavController(),
            onNext = {}
        )
    }
}