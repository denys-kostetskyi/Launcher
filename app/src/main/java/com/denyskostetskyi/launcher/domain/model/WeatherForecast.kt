package com.denyskostetskyi.launcher.domain.model

data class WeatherForecast(
    val temperature: Int,
    val weather: Weather,
    val location: String,
)
