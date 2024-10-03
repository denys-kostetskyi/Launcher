package com.denyskostetskyi.weatherforecast.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HourlyDataDto(
    @SerializedName("time")
    val timeArray: List<String>,
    @SerializedName("temperature_2m")
    val temperatureArray: List<Double>,
    @SerializedName("weather_code")
    val weatherCodeArray: List<Int>,
)
