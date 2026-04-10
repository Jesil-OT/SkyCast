package com.jesil.skycast.features.cities.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.features.weather.presentation.weatherUiState
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.generateBackgroundColor
import com.jesil.skycast.ui.util.generateIcon

@Composable
fun WeatherCityItem(
    modifier: Modifier = Modifier,
    item: WeatherStateUi,
    onLongPressState: Boolean,
    onClick: (lat: Double, lon: Double) -> Unit = {_,_ ->},
    onLongPress: (state: Boolean) -> Unit = {}
) {
    val backgroundColor = item.weatherTypeIcon.generateBackgroundColor()
    val backgroundColorStateTop = backgroundColor[0]
    val backgroundColorStateBottom = backgroundColor[1]
    Box(
        modifier = modifier.clip(
            shape = RoundedCornerShape(15.dp)
        ).background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    backgroundColorStateTop,
                    backgroundColorStateBottom
                )
            )
        ).clickable(
            onClick = { onClick(0.0, 0.0) }
        ),
        content = {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier,
                        content = {
                            Text(
                                text = item.location,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = item.location,
                                tint = Color.White,
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun WeatherCityItemPreview() {
    SkyCastTheme {
        WeatherCityItem(
            modifier = Modifier,
            item = weatherUiState,
            onClick = {_,_ ->},
            onLongPress = {},
            onLongPressState = false
        )
    }
}