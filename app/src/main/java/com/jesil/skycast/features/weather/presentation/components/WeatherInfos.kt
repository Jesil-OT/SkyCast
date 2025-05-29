package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jesil.skycast.R
import com.jesil.skycast.features.weather.models.WeatherStateUi
import com.jesil.skycast.ui.theme.RainyBlue2
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.Constants.TEMP_C

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeatherInfos(
    modifier: Modifier = Modifier,
    data: WeatherStateUi,
) {
    FlowRow(
        modifier = modifier.fillMaxSize(),
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.sunrise),
            value = data.sunrise,
            iconRes = ImageVector.vectorResource(R.drawable.ic_sunrise),
        )

        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.sunset),
            value = data.sunset,
            iconRes = ImageVector.vectorResource(R.drawable.ic_sunrise),
        )

        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.humidity),
            value = data.humidity,
            iconRes = ImageVector.vectorResource(R.drawable.ic_humidity),

        )

        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = "See level",
            value = data.seaLevel,
            iconRes = ImageVector.vectorResource(R.drawable.ic_sea_level_rise),
        )

        WeatherInfoItem(
            title = stringResource(R.string.visibility),
            value = data.visibility,
            iconRes = ImageVector.vectorResource(R.drawable.ic_visible),
        )

        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.wind_speed),
            value = data.windSpeed,
            iconRes = ImageVector.vectorResource(R.drawable.ic_wind),

        )

        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.pressure),
            value = data.pressure,
            iconRes = ImageVector.vectorResource(R.drawable.ic_pressure),

        )

        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.min_temp),
            value = data.minTemperature + TEMP_C,
            iconRes = ImageVector.vectorResource(R.drawable.ic_feels_like),

        )
    }
}

@PreviewLightDark
@Composable
private fun WeatherInfosPreview() {
    SkyCastTheme {
        WeatherInfos(
            modifier = Modifier.background(RainyBlue2),
            data = WeatherStateUi(
                sunrise = "3:55 am",
                sunset = "7:00 pm",
                humidity = "80%",
                windSpeed = "11 km/h",
                pressure = "1009 hpa",
                minTemperature = "29°",
                visibility = "10 km",
                seaLevel = "1009 hpa"
            )
        )
    }
}