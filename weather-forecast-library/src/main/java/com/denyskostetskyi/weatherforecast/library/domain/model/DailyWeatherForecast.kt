package com.denyskostetskyi.weatherforecast.library.domain.model

data class DailyWeatherForecast(
    val temperatureList: List<Double>,
    val weatherList: List<Weather>,
    val location: Location,
)
