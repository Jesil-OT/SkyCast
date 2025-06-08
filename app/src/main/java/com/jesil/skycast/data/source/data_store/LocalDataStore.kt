package com.jesil.skycast.data.source.data_store

import com.jesil.skycast.data.source.location.model.Location
import kotlinx.coroutines.flow.Flow

interface LocalDataStore {
    suspend fun saveLocation(location: Location)
    suspend fun getLocation(): Flow<Location>
}