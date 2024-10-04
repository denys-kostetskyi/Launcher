package com.denyskostetskyi.weatherforecast.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.denyskostetskyi.weatherforecast.data.local.entity.HourlyWeatherForecastEntity

@Dao
interface WeatherForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeatherForecasts(forecasts: List<HourlyWeatherForecastEntity>)

    @Query("SELECT * FROM weather_forecasts WHERE timeIso8601 = :time AND location = :location LIMIT 1")
    suspend fun getHourlyWeatherForecastByTimeAndLocation(
        time: String,
        location: String
    ): HourlyWeatherForecastEntity?

    @Query("DELETE FROM weather_forecasts WHERE location = :location")
    suspend fun deleteWeatherForecastForLocation(location: String)
}
