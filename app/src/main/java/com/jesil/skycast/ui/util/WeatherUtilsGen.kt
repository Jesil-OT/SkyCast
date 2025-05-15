package com.jesil.skycast.ui.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jesil.skycast.R
import com.jesil.skycast.ui.theme.CloudyBlue
import com.jesil.skycast.ui.theme.FoggyBlue
import com.jesil.skycast.ui.theme.RainyBlue
import com.jesil.skycast.ui.theme.ClearSky
import com.jesil.skycast.ui.theme.ClearSky2
import com.jesil.skycast.ui.theme.ClearSkyNight
import com.jesil.skycast.ui.theme.ClearSkyNight2
import com.jesil.skycast.ui.theme.CloudyBlue2
import com.jesil.skycast.ui.theme.CloudyBlueNight
import com.jesil.skycast.ui.theme.CloudyBlueNight2
import com.jesil.skycast.ui.theme.FoggyBlue2
import com.jesil.skycast.ui.theme.FoggyBlueNight
import com.jesil.skycast.ui.theme.FoggyBlueNight2
import com.jesil.skycast.ui.theme.Gray
import com.jesil.skycast.ui.theme.RainyBlue2
import com.jesil.skycast.ui.theme.RainyBlueNight
import com.jesil.skycast.ui.theme.RainyBlueNight2
import com.jesil.skycast.ui.theme.SnowyBlue
import com.jesil.skycast.ui.theme.SnowyBlue2
import com.jesil.skycast.ui.theme.SnowyBlueNight
import com.jesil.skycast.ui.theme.SnowyBlueNight2
import com.jesil.skycast.ui.theme.StormBlue
import com.jesil.skycast.ui.theme.StormBlue2
import com.jesil.skycast.ui.theme.StormBlueNight
import com.jesil.skycast.ui.theme.StormBlueNight2

fun String.generateIcon(): Int {
    return when(this) {
        //day icons
        "01d" -> R.drawable.ic_clear_sky
        "02d" -> R.drawable.ic_few_clouds
        "03d" -> R.drawable.ic_scattered_clouds
        "04d" -> R.drawable.ic_broken_clouds
        "09d" -> R.drawable.ic_shower_rain
        "10d" -> R.drawable.ic_rain
        "11d" -> R.drawable.ic_thunderstorm
        "13d" -> R.drawable.ic_snow
        "50d" -> R.drawable.ic_mist

        //night icons
        "01n" -> R.drawable.ic_clear_sky_night
        "02n" -> R.drawable.ic_few_clouds_night
        "03n" -> R.drawable.ic_scattered_clouds
        "04n" -> R.drawable.ic_broken_clouds
        "09n" -> R.drawable.ic_shower_rain_night
        "10n" -> R.drawable.ic_rain
        "11n" -> R.drawable.ic_thunderstorm_night
        "13n" -> R.drawable.ic_snow
        "50n" -> R.drawable.ic_mist

        else -> R.drawable.ic_launcher_background
    }
}

@Composable
fun String.generateBackgroundColor(): List<Color> {
    return when(this) {
        //day
        "01d" -> listOf(ClearSky2, ClearSky)
        "02d" -> listOf(CloudyBlue2, CloudyBlue)
        "03d" -> listOf(CloudyBlue2, CloudyBlue)
        "04d" -> listOf(CloudyBlue2, CloudyBlue)
        "09d" -> listOf(RainyBlue2, RainyBlue)
        "10d" -> listOf(RainyBlue2, RainyBlue)
        "11d" -> listOf(StormBlue2, StormBlue)
        "13d" -> listOf(SnowyBlue2, SnowyBlue)
        "50d" -> listOf(FoggyBlue2, FoggyBlue)

        //Night
        "01n" -> listOf(ClearSkyNight, ClearSkyNight2)
        "02n" -> listOf(CloudyBlueNight, CloudyBlueNight2)
        "03n" -> listOf(CloudyBlueNight, CloudyBlueNight2)
        "04n" -> listOf(CloudyBlueNight, CloudyBlueNight2)
        "09n" -> listOf(RainyBlueNight, RainyBlueNight2)
        "10n" -> listOf(RainyBlueNight, RainyBlueNight2)
        "11n" -> listOf(StormBlueNight, StormBlueNight2)
        "13n" -> listOf(SnowyBlueNight, SnowyBlueNight2)
        "50n" -> listOf(FoggyBlueNight, FoggyBlueNight2)
        else -> listOf(Gray, Color.White)
    }
}
