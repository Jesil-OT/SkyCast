package com.jesil.skycast.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.jesil.skycast.data.source.local.model.CityWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityWeatherDao {

    @Query("SELECT * FROM cities_weather_table ORDER BY id ASC")
    fun getAllCitiesWeather(): Flow<List<CityWeatherEntity>>

    @Query("SELECT * FROM cities_weather_table WHERE id = :id")
    fun getCityWeather(id: Int): Flow<CityWeatherEntity?>

    @Upsert
    suspend fun insertCityWeather(cityWeatherEntity: CityWeatherEntity)

    @Query("DELETE FROM cities_weather_table WHERE id = :id")
    suspend fun deleteCityWeather(id: Int)

    @Query("DELETE FROM cities_weather_table")
    suspend fun deleteAllCitiesWeather()

    @Update
    suspend fun updateCityWeather(cityWeatherEntity: CityWeatherEntity)
}