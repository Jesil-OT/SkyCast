package com.jesil.skycast.features.cities.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.skycast.features.cities.models.CityModel
import com.jesil.skycast.features.cities.presentation.fakeWeatherList
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.generateBackgroundColor
import com.jesil.skycast.ui.util.generateIcon

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherCityItem(
    modifier: Modifier = Modifier,
    item: CityModel,
    isSelected: Boolean,
    onClick: (id: Int) -> Unit,
    onLongPress: (state: Boolean) -> Unit = {}
) {
    val backgroundColor = item.weatherTypeIcon.generateBackgroundColor()
    val backgroundColorStateTop = backgroundColor[0]
    val backgroundColorStateBottom = backgroundColor[1]

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(15.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        backgroundColorStateTop,
                        backgroundColorStateBottom
                    )
                )
            )
            .combinedClickable(
                onClick = { onClick(item.id) },
                onLongClick = { onLongPress(!isSelected) }
            ),
        content = {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    AnimatedVisibility(
                        visible = isSelected,
                        content = {
                            Icon(
                                modifier = Modifier.padding(
                                    start = 10.dp
                                ),
                                imageVector = Icons.Filled.Menu,
                                contentDescription = null,
                                tint = Color.White,
                            )
                        }
                    )
                    Column(
                        modifier = Modifier.padding(start = 10.dp),
                        content = {
                            Text(
                                modifier = Modifier.fillMaxWidth(.5f),
                                text = item.location,
                                color = Color.White,
                                fontSize = 17.sp,
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            MinTempItem(
                                weatherIcon = item.weatherTypeIcon.generateIcon(),
                                minTemp = item.minTemperature,
                                weatherType = item.weatherType
                            )
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = item.maxTemperature,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontSize = 45.sp,
                        textAlign = TextAlign.Center,
                    )
                    AnimatedVisibility(
                        visible = isSelected,
                        content = {
                            Icon(
                                modifier = Modifier.padding(end = 10.dp),
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = null,
                                tint = Color.White,
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun MinTempItem(
    weatherIcon: Int,
    minTemp: String,
    weatherType: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(weatherIcon),
                contentDescription = weatherType
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = minTemp,
                color = Color.White,
                fontSize = 15.sp
            )
        }
    )
}

@Preview
@Composable
private fun WeatherCityItemPreview() {
    SkyCastTheme {
        WeatherCityItem(
            modifier = Modifier.fillMaxWidth(),
            item = fakeWeatherList[0],
            onClick = {},
            onLongPress = {},
            isSelected = true
        )
    }
}