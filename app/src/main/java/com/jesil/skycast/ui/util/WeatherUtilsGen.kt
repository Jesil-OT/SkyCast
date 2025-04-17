package com.jesil.skycast.ui.util

import androidx.compose.ui.graphics.Color
import com.jesil.skycast.R
import com.jesil.skycast.ui.theme.CloudyBlue
import com.jesil.skycast.ui.theme.FoggyBlue
import com.jesil.skycast.ui.theme.RainyBlue
import com.jesil.skycast.ui.theme.ClearSky
import com.jesil.skycast.ui.theme.ClearSkyNight
import com.jesil.skycast.ui.theme.CloudyBlueNight
import com.jesil.skycast.ui.theme.FoggyBlueNight
import com.jesil.skycast.ui.theme.RainyBlueNight
import com.jesil.skycast.ui.theme.SnowyBlue
import com.jesil.skycast.ui.theme.SnowyBlueNight
import com.jesil.skycast.ui.theme.StormBlue
import com.jesil.skycast.ui.theme.StormBlueNight

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

        else -> 0
    }
}

fun String.generateBackgroundColor(): Color? {
    return when(this) {
        //day
        "01d" -> ClearSky
        "02d" -> CloudyBlue
        "03d" -> CloudyBlue
        "04d" -> CloudyBlue
        "09d" -> RainyBlue
        "10d" -> RainyBlue
        "11d" -> StormBlue
        "13d" -> SnowyBlue
        "50d" -> FoggyBlue

        //Night
        "01n" -> ClearSkyNight
        "02n" -> CloudyBlueNight
        "03n" -> CloudyBlueNight
        "04n" -> CloudyBlueNight
        "09n" -> RainyBlueNight
        "10n" -> RainyBlueNight
        "11n" -> StormBlueNight
        "13n" -> SnowyBlueNight
        "50n" -> FoggyBlueNight
        else -> null
    }
}