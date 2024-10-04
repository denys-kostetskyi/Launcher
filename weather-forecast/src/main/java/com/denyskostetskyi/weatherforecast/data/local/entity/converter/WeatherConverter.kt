package com.denyskostetskyi.weatherforecast.data.local.entity.converter

import androidx.room.TypeConverter
import com.denyskostetskyi.weatherforecast.library.domain.model.Weather

class WeatherConverter {
    @TypeConverter
    fun fromWeather(weather: Weather): String {
        return weather.name
    }

    @TypeConverter
    fun toWeather(weatherString: String): Weather {
        return Weather.valueOf(weatherString)
    }
}
