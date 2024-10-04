package com.denyskostetskyi.launcher.presentation.state

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast
import com.denyskostetskyi.weatherforecast.library.domain.model.Weather
import java.util.Locale

class WeatherForecastMapper {
    fun mapToState(weatherForecast: HourlyWeatherForecast): WeatherForecastState {
        val temperature = formatTemperature(weatherForecast.temperature)
        val weather = getWeather(weatherForecast.weather)
        val weatherIcon = getWeatherIcon(weatherForecast.weather)
        return WeatherForecastState(
            temperature = temperature,
            weather = weather,
            location = weatherForecast.location.name,
            weatherIcon = weatherIcon,
        )
    }

    private fun formatTemperature(temperature: Double) =
        String.format(Locale.getDefault(), "%.1f", temperature)

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
        Weather.CLEAR -> R.drawable.weather_clear
        Weather.CLOUDY -> R.drawable.weather_cloudy
        Weather.FOG -> R.drawable.weather_fog
        Weather.RAIN -> R.drawable.weather_rain
        Weather.SNOW -> R.drawable.weather_snow
        Weather.THUNDERSTORM -> R.drawable.weather_thunder
        Weather.UNKNOWN -> R.drawable.weather_unknown
    }
}
