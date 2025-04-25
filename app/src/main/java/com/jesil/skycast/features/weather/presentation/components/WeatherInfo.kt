package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.jesil.skycast.R
import com.jesil.skycast.features.weather.data.WeatherDataUi
import com.jesil.skycast.ui.theme.ClearSky
import com.jesil.skycast.ui.theme.SkyCastTheme

@Composable
fun WeatherInfo(
    modifier: Modifier = Modifier,
    data: WeatherDataUi,
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (sunriseItem, sunsetItem, humidityItem, windSpeedItem, pressureItem, minTempItem) = createRefs()
        createHorizontalChain(sunriseItem, sunsetItem, chainStyle = ChainStyle.SpreadInside)
        createHorizontalChain(humidityItem, windSpeedItem, chainStyle = ChainStyle.SpreadInside)
        createHorizontalChain(pressureItem, minTempItem, chainStyle = ChainStyle.SpreadInside)

        WeatherInfoItem(
            title = stringResource(R.string.sunrise),
            value = data.sunrise,
            iconRes = painterResource(R.drawable.ic_sunrise),
            modifier = Modifier.constrainAs(sunriseItem) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        WeatherInfoItem(
            title = stringResource(R.string.sunset),
            value = data.sunset,
            iconRes = painterResource(R.drawable.ic_sunrise),
            modifier = Modifier.constrainAs(sunsetItem) {}
        )

        WeatherInfoItem(
            title = stringResource(R.string.humidity),
            value = data.humidity,
            iconRes = painterResource(R.drawable.ic_humidity),
            modifier = Modifier.constrainAs(humidityItem) {
                top.linkTo(sunriseItem.bottom, margin = 10.dp)
                start.linkTo(sunriseItem.start)
            }
        )

        WeatherInfoItem(
            title = stringResource(R.string.wind_speed),
            value = data.windSpeed,
            iconRes = painterResource(R.drawable.ic_wind),
            modifier = Modifier.constrainAs(windSpeedItem) {
                top.linkTo(sunriseItem.bottom, margin = 10.dp)
//                end.linkTo(sunriseItem.end)
            }
        )

        WeatherInfoItem(
            title = stringResource(R.string.pressure),
            value = data.pressure,
            iconRes = painterResource(R.drawable.ic_feels_like),
            modifier = Modifier.constrainAs(pressureItem) {
                top.linkTo(humidityItem.bottom, margin = 10.dp)
                start.linkTo(humidityItem.start)
            }
        )

        WeatherInfoItem(
            title = stringResource(R.string.min_temp),
            value = data.minTemperature,
            iconRes = painterResource(R.drawable.ic_precipitation),
            modifier = Modifier.constrainAs(minTempItem) {
                top.linkTo(humidityItem.bottom, margin = 10.dp)
            }
        )
    }
}

@Preview
@Composable
private fun WeatherInfoPreview() {
    SkyCastTheme {
        WeatherInfo(
            modifier = Modifier.background(ClearSky),
            data = WeatherDataUi(
                sunrise = "3:55 am",
                sunset = "7:00 pm",
                humidity = "80%",
                windSpeed = "11 km/h",
                pressure = "1009 hpa",
                minTemperature = "29°C",
            )
        )
    }

}