package com.denyskostetskyi.weatherforecast.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DailyWeatherForecastDto(
    @SerializedName("hourly")
    val hourlyData: HourlyDataDto,
)
