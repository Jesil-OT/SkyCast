package com.jesil.skycast.features.location.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/***
 * A custom button for location permission
 * **/
@Composable
fun LocationButton(
    modifier: Modifier = Modifier,
    icon : @Composable () -> Unit,
    text: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) { icon.invoke(); text.invoke() }
    }
}