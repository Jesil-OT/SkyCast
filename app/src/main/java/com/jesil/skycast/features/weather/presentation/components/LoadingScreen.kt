package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesil.skycast.R

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimationPreloader(
            modifier = modifier.size(150.dp),
            lottieId = R.raw.loading_weather
        )
    }
}