package com.jesil.skycast.features.cities.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jesil.skycast.ui.theme.SkyCastTheme

@Composable
fun SearchBarDisplay(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val boxColor = if (isSystemInDarkTheme()) Color.White else Color.Gray.copy(.5f)
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(boxColor)
            .clickable(onClick = onClick),
        content = {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Black.copy(.5f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Search for a location",
                    color = Color.Black.copy(.5f)
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun SearchBarDisplayPreview() {
    SkyCastTheme {
        SearchBarDisplay()
    }
}