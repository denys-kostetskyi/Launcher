package com.denyskostetskyi.launcher.presentation.state

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.domain.model.Weather
import com.denyskostetskyi.launcher.domain.model.WeatherForecast

class WeatherForecastMapper {
    fun mapToState(weatherForecast: WeatherForecast): WeatherForecastState {
        val weather = getWeather(weatherForecast.weather)
        val weatherIcon = getWeatherIcon(weatherForecast.weather)
        return WeatherForecastState(
            temperature = weatherForecast.temperature,
            weather = weather,
            location = weatherForecast.location,
            weatherIcon = weatherIcon,
        )
    }

    @StringRes
    private fun getWeather(weather: Weather) = when (weather) {
        Weather.CLEAR -> R.string.weather_clear
    }

    @DrawableRes
    private fun getWeatherIcon(weather: Weather) = when (weather) {
        Weather.CLEAR -> R.drawable.weather_day_clear
    }
}
