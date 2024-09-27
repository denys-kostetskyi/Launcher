package com.denyskostetskyi.launcher.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherForecast(forecast: WeatherForecastDbModel)

    @Query("SELECT * FROM weather_forecast WHERE location = :location LIMIT 1")
    fun getWeatherForecast(location: String): LiveData<WeatherForecastDbModel>
}
