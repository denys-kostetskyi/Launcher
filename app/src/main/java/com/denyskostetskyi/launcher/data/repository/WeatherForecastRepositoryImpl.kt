package com.denyskostetskyi.launcher.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.denyskostetskyi.launcher.data.local.WeatherForecastDao
import com.denyskostetskyi.launcher.data.mapper.WeatherForecastMapper
import com.denyskostetskyi.launcher.domain.model.Weather
import com.denyskostetskyi.launcher.domain.model.WeatherForecast
import com.denyskostetskyi.launcher.domain.repository.WeatherForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class WeatherForecastRepositoryImpl(
    private val weatherForecastDao: WeatherForecastDao,
    private val mapper: WeatherForecastMapper,
) : WeatherForecastRepository {
    override suspend fun fetchWeatherForecast(location: String): Result<Unit> {
        withContext(Dispatchers.IO) {
            val weatherForecast = mockWeatherForecast(location)
            val dbModel = mapper.mapEntityToDbModel(weatherForecast)
            weatherForecastDao.insertWeatherForecast(dbModel)
        }
        return Result.success(Unit)
    }

    override fun getWeatherForecast(location: String): LiveData<WeatherForecast> {
        return weatherForecastDao.getWeatherForecast(location).map(mapper::mapDbModelToEntity)
    }

    private fun mockWeatherForecast(location: String): WeatherForecast {
        return WeatherForecast(
            temperature = Random.nextInt(from = 1, until = 30),
            weather = Weather.CLEAR,
            location = location
        )
    }
}
