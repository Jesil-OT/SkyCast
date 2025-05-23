package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jesil.skycast.R
import com.jesil.skycast.ui.theme.SkyCastTheme

@Composable
fun ErrorScreen(
    message: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        LottieAnimationPreloader(
            modifier = Modifier.size(24.dp),
            lottieId = R.raw.error
        )

        Text(
            text = message,
            modifier = Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Button(
            onClick = onRetryClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.try_again),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ErrorScreenPreview() {
    SkyCastTheme {
        ErrorScreen(
            message = "Something went wrong",
            onRetryClick = {}
        )
    }
}