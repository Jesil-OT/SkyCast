package com.jesil.skycast.data.source.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.jesil.skycast.data.source.location.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber

class LocalDataStoreImpl(
    private val context: Context
) : LocalDataStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "location")

    override suspend fun saveLocation(location: Location) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LATITUDE] = location.latitude
            preferences[PreferencesKeys.LONGITUDE] = location.longitude
        }
    }

    override suspend fun getLocation(): Flow<Location> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                Timber.d("Error reading preferences: ${exception.message}")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val latitude = preferences[PreferencesKeys.LATITUDE] ?: 0.0
            val longitude = preferences[PreferencesKeys.LONGITUDE] ?: 0.0
            Location(latitude, longitude)
        }
    }

    private object PreferencesKeys {
        val LATITUDE = doublePreferencesKey("latitude")
        val LONGITUDE = doublePreferencesKey("longitude")
    }
}