package com.denyskostetskyi.launcher.data.repository

import androidx.lifecycle.LiveData
import com.denyskostetskyi.launcher.domain.model.Weather
import com.denyskostetskyi.launcher.domain.model.WeatherForecast
import com.denyskostetskyi.launcher.domain.repository.WeatherForecastRepository
import kotlin.random.Random

class WeatherForecastRepositoryImpl : WeatherForecastRepository {
    override fun fetchWeatherForecast(location: String): Result<Unit> {
        val weatherForecast = mockWeatherForecast(location)
        //TODO save forecast to the database
        return Result.success(Unit)
    }

    override fun getWeatherForecast(): LiveData<WeatherForecast> {
        TODO("Not yet implemented")
    }

    private fun mockWeatherForecast(location: String): WeatherForecast {
        return WeatherForecast(
            temperature = Random.nextInt(from = 1, until = 30),
            weather = Weather.CLEAR,
            location = location
        )
    }
}