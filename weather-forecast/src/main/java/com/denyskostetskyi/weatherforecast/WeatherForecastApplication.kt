package com.denyskostetskyi.weatherforecast

import android.app.Application
import com.denyskostetskyi.weatherforecast.data.local.AppDatabase
import com.denyskostetskyi.weatherforecast.data.mapper.WeatherForecastMapper
import com.denyskostetskyi.weatherforecast.data.remote.RetrofitApi
import com.denyskostetskyi.weatherforecast.data.repository.WeatherForecastRepositoryImpl
import com.denyskostetskyi.weatherforecast.domain.repository.WeatherForecastRepository

class WeatherForecastApplication : Application() {
    lateinit var weatherForecastRepository: WeatherForecastRepository

    override fun onCreate() {
        super.onCreate()
        val apiService = RetrofitApi.openMeteoApiService
        val dao = AppDatabase.getInstance(this).weatherForecastDao()
        val mapper = WeatherForecastMapper()
        weatherForecastRepository = WeatherForecastRepositoryImpl(apiService, dao, mapper)
    }
}
