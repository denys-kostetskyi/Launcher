package com.denyskostetskyi.launcher.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.denyskostetskyi.launcher.domain.model.Weather

@Entity(tableName = "weather_forecast")
data class WeatherForecastDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val temperature: Int,
    val weather: Weather,
    val location: String
)
