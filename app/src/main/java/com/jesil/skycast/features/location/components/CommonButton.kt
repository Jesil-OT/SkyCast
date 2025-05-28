package com.jesil.skycast.features.location.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.skycast.R
import com.jesil.skycast.features.location.LocationAction


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