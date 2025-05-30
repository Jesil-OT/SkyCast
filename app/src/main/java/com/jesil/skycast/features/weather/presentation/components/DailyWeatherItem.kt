package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.skycast.ui.theme.SkyCastTheme
import com.jesil.skycast.ui.util.generateIcon

@Composable
fun DailyWeatherItem(
    modifier: Modifier = Modifier,
    day: String,
    weatherType: String,
    temperature: String,
    minTemperature: String
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Text(
                text = day,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.displayMedium.copy(
                    color = Color.White,
                    fontSize = 17.sp
                )
            )

            Image(
                painter = painterResource(weatherType.generateIcon()),
                contentDescription = weatherType,
                modifier = Modifier
                    .size(30.dp)
                    .weight(1f)
            )
            Text(
                text = temperature,
                modifier = Modifier,
                style = MaterialTheme.typography.displayMedium.copy(
                    color = Color.White,
                    fontSize = 15.sp
                )
            )
            Spacer(Modifier.widthIn(20.dp))
            Text(
                text = minTemperature,
                style = MaterialTheme.typography.displayMedium.copy(
                    color = Color.White.copy(alpha = .5f),
                    fontSize = 15.sp
                )
            )

        }
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp),
            thickness = 1.dp,
            color = Color.White.copy(.2f)
        )
    }
}

@Preview
@Composable
private fun DailyWeatherItemPreview() {
    SkyCastTheme {
        DailyWeatherItem(
            day = "Monday",
            weatherType = "01n",
            temperature = "31°",
            minTemperature = "29°",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan),
        )
    }
}