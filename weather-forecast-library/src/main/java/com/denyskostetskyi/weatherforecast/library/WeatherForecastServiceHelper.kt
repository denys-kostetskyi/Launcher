package com.denyskostetskyi.weatherforecast.library

import android.content.ComponentName
import android.content.Intent

class WeatherForecastServiceHelper {
    companion object {
        private const val PACKAGE_NAME = "com.denyskostetskyi.weatherforecast"
        private const val CLASS_NAME = "$PACKAGE_NAME.WeatherForecastService"

        fun newIntent() = Intent().apply {
            setComponent(ComponentName(PACKAGE_NAME, CLASS_NAME))
        }
    }
}
