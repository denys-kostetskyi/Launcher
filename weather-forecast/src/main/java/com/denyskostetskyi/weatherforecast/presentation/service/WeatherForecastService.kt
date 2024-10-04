package com.denyskostetskyi.weatherforecast.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.denyskostetskyi.weatherforecast.domain.repository.WeatherForecastRepository
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast
import com.denyskostetskyi.weatherforecast.library.domain.model.Location

class WeatherForecastService : Service() {
    private lateinit var repository: WeatherForecastRepository

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private suspend fun getHourlyWeatherForecast(
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
