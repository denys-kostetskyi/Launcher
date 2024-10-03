package com.denyskostetskyi.weatherforecast.library.domain.model

data class HourlyWeatherForecast(
    val temperature: Double,
    val weather: Weather,
    val location: Location,
)
