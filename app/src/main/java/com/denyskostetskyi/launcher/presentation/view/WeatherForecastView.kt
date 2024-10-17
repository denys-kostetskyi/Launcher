package com.denyskostetskyi.launcher.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.presentation.state.WeatherForecastState

class WeatherForecastView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val textViewTemperature: TextView
    private val textViewWeather: TextView
    private val textViewLocation: TextView
    private val imageViewWeather: ImageView

    init {
        View.inflate(context, R.layout.weather_forecast_view_layout, this)
        textViewTemperature = findViewById(R.id.textViewTemperature)
        textViewWeather = findViewById(R.id.textViewWeather)
        textViewLocation = findViewById(R.id.textViewLocation)
        imageViewWeather = findViewById(R.id.imageViewWeather)
    }

    fun updateState(state: WeatherForecastState) {
        val temperature = context.getString(R.string.temperature_celsius, state.temperature)
        val weather = context.getString(state.weather)
        val weatherIcon = ContextCompat.getDrawable(context, state.weatherIcon)
        textViewTemperature.text = temperature
        textViewWeather.text = weather
        textViewLocation.text = state.location
        imageViewWeather.setImageDrawable(weatherIcon)
    }
}
