package com.denyskostetskyi.weatherforecast.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HourlyDataDto(
    @SerializedName("time")
    val timeList: List<String>,
    @SerializedName("temperature_2m")
    val temperatureList: List<Double>,
    @SerializedName("weather_code")
    val weatherCodeList: List<Int>,
)
