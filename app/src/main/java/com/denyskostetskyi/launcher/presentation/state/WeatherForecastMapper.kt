package com.denyskostetskyi.launcher.presentation.state

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.weatherforecast.library.domain.model.Weather
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast

class WeatherForecastMapper {
    fun mapToState(weatherForecast: HourlyWeatherForecast): WeatherForecastState {
        val weather = getWeather(weatherForecast.weather)
        val weatherIcon = getWeatherIcon(weatherForecast.weather)
        return WeatherForecastState(
            temperature = weatherForecast.temperature,
            weather = weather,
            location = weatherForecast.location.name,
            weatherIcon = weatherIcon,
        )
    }

    @StringRes
    private fun getWeather(weather: Weather) = when (weather) {
        Weather.CLEAR -> R.string.weather_clear
        Weather.CLOUDY -> R.string.weather_cloudy
        Weather.FOG -> R.string.weather_fog
        Weather.RAIN -> R.string.weather_rain
        Weather.SNOW -> R.string.weather_snow
        Weather.THUNDERSTORM -> R.string.weather_thunderstorm
        Weather.UNKNOWN -> R.string.weather_unknown
    }

    @DrawableRes
    private fun getWeatherIcon(weather: Weather) = when (weather) {
        Weather.CLEAR -> R.drawable.weather_day_clear
        else -> R.drawable.weather_unknown //TODO add rest of weather icons
    }
}
