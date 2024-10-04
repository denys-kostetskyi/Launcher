package com.denyskostetskyi.weatherforecast.data.repository

import com.denyskostetskyi.weatherforecast.data.local.WeatherForecastDao
import com.denyskostetskyi.weatherforecast.data.mapper.WeatherForecastMapper
import com.denyskostetskyi.weatherforecast.data.remote.OpenMeteoApiService
import com.denyskostetskyi.weatherforecast.domain.repository.WeatherForecastRepository
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast
import com.denyskostetskyi.weatherforecast.library.domain.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherForecastRepositoryImpl(
    private val apiService: OpenMeteoApiService,
    private val dao: WeatherForecastDao,
    private val mapper: WeatherForecastMapper,
) : WeatherForecastRepository {
    private val dispatcher = Dispatchers.IO

    override suspend fun fetchDailyWeatherForecast(location: Location): Result<Unit> {
        return withContext(dispatcher) {
            try {
                val dto = apiService.getWeatherForecast(location.latitude, location.longitude)
                val entityList = mapper.dtoToEntityList(dto, location)
                dao.deleteWeatherForecastForLocation(location.name)
                dao.insertHourlyWeatherForecasts(entityList)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getHourlyWeatherForecast(
        location: Location,
        dateTime: String
    ): Result<HourlyWeatherForecast> {
        return withContext(dispatcher) {
            try {
                val result = getHourlyWeatherForecastInternal(location, dateTime)
                if (result.isFailure) {
                    val fetchResult = fetchDailyWeatherForecast(location)
                    if (fetchResult.isSuccess) {
                        getHourlyWeatherForecastInternal(location, dateTime)
                    } else {
                        val reason = fetchResult.exceptionOrNull().toString()
                        Result.failure(RuntimeException("Unable to fetch weather data for location: $location Reason: $reason"))
                    }
                } else {
                    result
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private suspend fun getHourlyWeatherForecastInternal(
        location: Location,
        dateTime: String,
    ): Result<HourlyWeatherForecast> {
        return withContext(dispatcher) {
            val entity = dao.getHourlyWeatherForecastByTimeAndLocation(dateTime, location.name)
            if (entity != null) {
                val model = mapper.entityToModel(entity, location)
                Result.success(model)
            } else {
                Result.failure(RuntimeException("No weather data available for time: $dateTime"))
            }
        }
    }
}
