package com.jesil.skycast.data.source.location

import com.jesil.skycast.data.source.location.model.Location
import com.jesil.skycast.ui.util.Response

interface LocationTracker {
    suspend fun getCurrentLocation(): Response<Location>
}