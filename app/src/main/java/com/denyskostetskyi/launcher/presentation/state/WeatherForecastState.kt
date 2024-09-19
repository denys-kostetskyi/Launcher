package com.denyskostetskyi.launcher.presentation.state

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WeatherForecastState(
    val temperature: Int,
    @StringRes val weather: Int,
    val location: String,
    @DrawableRes val weatherIcon: Int,
)
