package com.denyskostetskyi.launcher.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.databinding.WeatherForecastViewLayoutBinding
import com.denyskostetskyi.launcher.presentation.state.WeatherForecastState

class WeatherForecastView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val binding = WeatherForecastViewLayoutBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun updateState(state: WeatherForecastState) {
        val temperature = context.getString(R.string.temperature_celsius, state.temperature)
        val weather = context.getString(state.weather)
        val weatherIcon = ContextCompat.getDrawable(context, state.weatherIcon)
        with(binding) {
            textViewTemperature.text = temperature
            textViewWeather.text = weather
            textViewLocation.text = state.location
            imageViewWeather.setImageDrawable(weatherIcon)
        }
    }
}
