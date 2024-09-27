package com.denyskostetskyi.launcher.domain.repository

import androidx.lifecycle.LiveData
import com.denyskostetskyi.launcher.domain.model.WeatherForecast

interface WeatherForecastRepository {
    suspend fun fetchWeatherForecast(location: String): Result<Unit>

    fun getWeatherForecast(location: String): LiveData<WeatherForecast>
}
