package com.denyskostetskyi.weatherforecast.data.remote

import com.denyskostetskyi.weatherforecast.data.remote.dto.DailyWeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApiService {
    @GET(OpenMeteoApiConstants.ENDPOINT_FORECAST)
    suspend fun getWeatherForecast(
        @Query(OpenMeteoApiConstants.PARAM_LATITUDE) latitude: Double,
        @Query(OpenMeteoApiConstants.PARAM_LONGITUDE) longitude: Double,
        @Query(OpenMeteoApiConstants.PARAM_HOURLY) hourly: String = OpenMeteoApiConstants.DEFAULT_HOURLY,
        @Query(OpenMeteoApiConstants.PARAM_TIMEZONE) timezone: String = OpenMeteoApiConstants.DEFAULT_TIMEZONE,
        @Query(OpenMeteoApiConstants.PARAM_FORECAST_DAYS) forecastDays: Int = OpenMeteoApiConstants.DEFAULT_FORECAST_DAYS
    ): DailyWeatherForecastDto
}
