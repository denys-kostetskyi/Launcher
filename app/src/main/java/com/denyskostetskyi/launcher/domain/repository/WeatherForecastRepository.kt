package com.denyskostetskyi.launcher.domain.repository

import androidx.lifecycle.LiveData
import com.denyskostetskyi.launcher.domain.model.WeatherForecast

interface WeatherForecastRepository {
    fun fetchWeatherForecast(location: String): Result<Unit>

    fun getWeatherForecast(): LiveData<WeatherForecast>
}