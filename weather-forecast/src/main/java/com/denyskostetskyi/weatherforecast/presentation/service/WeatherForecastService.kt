package com.denyskostetskyi.weatherforecast.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.denyskostetskyi.weatherforecast.WeatherForecastApplication
import com.denyskostetskyi.weatherforecast.domain.repository.WeatherForecastRepository
import com.denyskostetskyi.weatherforecast.library.IWeatherForecastService
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast
import com.denyskostetskyi.weatherforecast.library.domain.model.Location
import kotlinx.coroutines.runBlocking

class WeatherForecastService : Service() {
    private lateinit var repository: WeatherForecastRepository

    private val binder = object : IWeatherForecastService.Stub() {
        override fun getHourlyWeatherForecast(
            location: Location,
            dateTime: String
        ): HourlyWeatherForecast? {
            return runBlocking {
                getHourlyWeatherForecastInternal(location, dateTime)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        repository = (application as WeatherForecastApplication).weatherForecastRepository
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind")
        return binder
    }

    private suspend fun getHourlyWeatherForecastInternal(
        location: Location,
        dateTime: String
    ): HourlyWeatherForecast? {
        val result = repository.getHourlyWeatherForecast(location, dateTime)
        return if (result.isFailure) {
            Log.e(TAG, result.exceptionOrNull().toString())
            null
        } else {
            result.getOrNull()
        }
    }

    companion object {
        private const val TAG = "WeatherForecastService"
    }
}
