package com.jesil.skycast.features.cities.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jesil.skycast.core.navigation.Screens
import com.jesil.skycast.features.cities.presentation.components.SearchBarDisplay
import com.jesil.skycast.ui.theme.SkyCastTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                title = {}
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
            ) {
                Text(
                    modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                    text = "Manage Cities",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 27.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                SearchBarDisplay(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }
        }
    )
}


@PreviewLightDark
@Composable
private fun CitiesScreenPreview() {
    SkyCastTheme {
        CitiesScreen(
            navController = rememberNavController()
        )
    }
}