package com.denyskostetskyi.weatherforecast.library

import android.content.ComponentName
import android.content.Intent
import android.text.format.DateFormat
import java.util.Calendar

class WeatherForecastServiceHelper {
    companion object {
        private const val PACKAGE_NAME = "com.denyskostetskyi.weatherforecast"
        private const val CLASS_NAME = "$PACKAGE_NAME.presentation.service.WeatherForecastService"
        private const val ISO_8601_STRING_FORMAT = "yyyy-MM-dd'T'HH:mm"

        fun newIntent() = Intent().apply {
            setComponent(ComponentName(PACKAGE_NAME, CLASS_NAME))
        }

        fun getTimeString(): String {
            val calendar = Calendar.getInstance().apply { set(Calendar.MINUTE, 0) }
            return DateFormat.format(ISO_8601_STRING_FORMAT, calendar).toString()
        }
    }
}
