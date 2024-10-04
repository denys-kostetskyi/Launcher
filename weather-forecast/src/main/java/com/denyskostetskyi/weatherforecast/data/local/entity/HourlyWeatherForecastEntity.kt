package com.denyskostetskyi.weatherforecast.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.denyskostetskyi.weatherforecast.library.domain.model.Weather

@Entity(tableName = "weather_forecasts")
data class HourlyWeatherForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timeIso8601: String,
    val temperature: Double,
    val weather: Weather,
    val location: String,
)
