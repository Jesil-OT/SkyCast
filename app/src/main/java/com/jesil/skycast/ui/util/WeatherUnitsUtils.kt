package com.jesil.skycast.ui.util

import com.jesil.skycast.ui.util.Constants.MS_TO_KMH
import com.jesil.skycast.ui.util.Constants.TEMP_CELSIUS
import com.jesil.skycast.ui.util.Constants.TEMP_IN_CELSIUS
import kotlin.math.roundToInt

internal fun Double.convertToCelsius(): Int {
    return (this - TEMP_IN_CELSIUS).roundToInt()
//        .toString() + TEMP_CELSIUS
}

internal fun Double.convertMsToKhm(): Int{
    return (this * MS_TO_KMH).roundToInt()
}