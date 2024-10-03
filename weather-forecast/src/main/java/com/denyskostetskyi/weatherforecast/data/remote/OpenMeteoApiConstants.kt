package com.denyskostetskyi.weatherforecast.data.remote

object OpenMeteoApiConstants {
    const val BASE_URL = "https://api.open-meteo.com/v1/"

    const val ENDPOINT_FORECAST = "forecast"

    const val PARAM_LATITUDE = "latitude"
    const val PARAM_LONGITUDE = "longitude"
    const val PARAM_HOURLY = "hourly"
    const val PARAM_TIMEZONE = "timezone"
    const val PARAM_FORECAST_DAYS = "forecast_days"

    const val DEFAULT_HOURLY = "temperature_2m,weather_code"
    const val DEFAULT_TIMEZONE = "auto"
    const val DEFAULT_FORECAST_DAYS = 1
}
