package com.denyskostetskyi.weatherforecast.data.mapper

import com.denyskostetskyi.weatherforecast.data.local.entity.HourlyWeatherForecastEntity
import com.denyskostetskyi.weatherforecast.data.remote.dto.DailyWeatherForecastDto
import com.denyskostetskyi.weatherforecast.data.remote.dto.HourlyDataDto
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast
import com.denyskostetskyi.weatherforecast.library.domain.model.Location
import com.denyskostetskyi.weatherforecast.library.domain.model.Weather

class WeatherForecastMapper {
    fun dtoToEntityList(
        dto: DailyWeatherForecastDto,
        location: Location
    ): List<HourlyWeatherForecastEntity> {
        val data = dto.hourlyData
        ensureHourlyDataListSizes(data)
        return (0 until HOURLY_LIST_SIZE).map { index ->
            val dateTime = data.timeList[index]
            val temperature = data.temperatureList[index]
            val weather = getWeatherFromCode(data.weatherCodeList[index])
            HourlyWeatherForecastEntity(
                timeIso8601 = dateTime,
                temperature = temperature,
                weather = weather,
                location = location.name
            )
        }
    }

    fun entityToModel(entity: HourlyWeatherForecastEntity, location: Location) =
        HourlyWeatherForecast(
            dateTime = entity.timeIso8601,
            temperature = entity.temperature,
            weather = entity.weather,
            location = location
        )

    private fun ensureHourlyDataListSizes(dto: HourlyDataDto) {
        val timeListSize = dto.timeList.size
        val temperatureListSize = dto.temperatureList.size
        val weatherCodeListSize = dto.weatherCodeList.size
        if (timeListSize != HOURLY_LIST_SIZE) {
            throw IllegalArgumentException("Expected timeList size: $HOURLY_LIST_SIZE actual: $timeListSize")
        }
        if (dto.temperatureList.size != HOURLY_LIST_SIZE) {
            throw IllegalArgumentException("Expected temperatureList size: $HOURLY_LIST_SIZE actual: $temperatureListSize")
        }
        if (dto.weatherCodeList.size != 24) {
            throw IllegalArgumentException("Expected weatherCodeList size: $HOURLY_LIST_SIZE actual: $weatherCodeListSize")
        }
    }

    // https://open-meteo.com/en/docs
    private fun getWeatherFromCode(code: Int): Weather {
        return when (code) {
            0 -> Weather.CLEAR
            in 1..3 -> Weather.CLOUDY
            45, 48 -> Weather.FOG
            in 51..57, in 61..67, in 80..82 -> Weather.RAIN
            in 71..75, 77, 85, 86 -> Weather.SNOW
            95, 96, 99 -> Weather.THUNDERSTORM
            else -> Weather.UNKNOWN
        }
    }

    companion object {
        private const val HOURLY_LIST_SIZE = 24
    }
}
