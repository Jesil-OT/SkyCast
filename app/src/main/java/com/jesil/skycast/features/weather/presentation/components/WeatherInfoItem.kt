package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.skycast.R
import com.jesil.skycast.data.source.location.model.Location
import com.jesil.skycast.ui.theme.RainyBlue2
import com.jesil.skycast.ui.theme.SkyCastTheme

@Composable
fun WeatherInfoItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    iconRes: ImageVector
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.background.copy(.2f),
        tonalElevation = 100.dp
    ){
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
           Row(verticalAlignment = Alignment.CenterVertically) {
               Text(
                   modifier = Modifier.weight(1f),
                   text = title.uppercase(),
                   style = MaterialTheme.typography.labelMedium.copy(
                       color = Color.White.copy(alpha = .5f),
                       fontWeight = FontWeight.Bold,
                       fontSize = 15.sp,
                   ),
                   maxLines = 1,
                   overflow = TextOverflow.Ellipsis
               )

               Icon(
                   modifier = Modifier.size(24.dp),
                   imageVector = iconRes,
                   contentDescription = title,
                   tint = Color.White.copy(alpha = .5f),
               )
           }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        }

    }
}

@Composable
fun Location(
    modifier: Modifier = Modifier,
    location: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = location,
            tint = Color.White,
        )

        Text(
            text =location,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@PreviewLightDark
@Composable
private fun WeatherInfoItemPreview() {
    SkyCastTheme {
        WeatherInfoItem(
            title = "Humidity",
            value = "80%",
            iconRes = ImageVector.vectorResource(id = R.drawable.ic_humidity),
            modifier = Modifier.background(RainyBlue2)
        )
    }
}