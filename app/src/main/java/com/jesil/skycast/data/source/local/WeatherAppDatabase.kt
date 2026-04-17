package com.jesil.skycast.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jesil.skycast.data.source.local.model.CityWeatherEntity
//import com.jesil.skycast.data.source.local.model.Converters

@Database(
    entities = [CityWeatherEntity::class],
    version = 3,
    exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class WeatherAppDatabase: RoomDatabase() {
    abstract fun dao(): CityWeatherDao
}