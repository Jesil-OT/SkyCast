package com.jesil.skycast.ui.util

import androidx.compose.ui.text.toLowerCase
import com.jesil.skycast.ui.util.Constants.MS_TO_KMH
import com.jesil.skycast.ui.util.Constants.TEMP_IN_CELSIUS
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

internal fun Double.convertToCelsius(): Int {
    return (this - TEMP_IN_CELSIUS).roundToInt()
}

internal fun Double.convertMsToKhm(): Int {
    return (this * MS_TO_KMH).roundToInt()
}

fun Instant.formatUnixTime(zoneId: String = "UTC"): String =
    DateTimeFormatter.ofPattern("h:mm a")
        .withZone(ZoneId.of(zoneId)).format(this).lowercase(Locale.ROOT)

fun Instant.formatUnixTimeSimple(zoneId: String = "UTC"): String =
    DateTimeFormatter.ofPattern("h a")
        .withZone(ZoneId.of(zoneId)).format(this).lowercase(Locale.ROOT)

fun Instant.formatUnixDay(zoneId: String = "UTC"): String =
    DateTimeFormatter.ofPattern("EEEE")
        .withZone(ZoneId.of(zoneId)).format(this)

fun Instant.formatTimestamp(zoneId: String = "UTC"): String =
    DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH)
        .withZone(ZoneId.of(zoneId)).format(this)