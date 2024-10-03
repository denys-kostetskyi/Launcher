package com.denyskostetskyi.weatherforecast.domain.repository

import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast
import com.denyskostetskyi.weatherforecast.library.domain.model.Location

interface WeatherForecastRepository {
    suspend fun fetchDailyWeatherForecast(location: Location): Result<Unit>

    suspend fun getHourlyWeatherForecast(
        location: Location,
        dateTime: String
    ): Result<HourlyWeatherForecast>
}
