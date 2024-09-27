package com.denyskostetskyi.launcher.data.mapper

import com.denyskostetskyi.launcher.data.local.WeatherForecastDbModel
import com.denyskostetskyi.launcher.domain.model.WeatherForecast

class WeatherForecastMapper {
    fun mapEntityToDbModel(entity: WeatherForecast) = WeatherForecastDbModel(
        temperature = entity.temperature,
        weather = entity.weather,
        location = entity.location
    )

    fun mapDbModelToEntity(dbModel: WeatherForecastDbModel) = WeatherForecast(
        temperature = dbModel.temperature,
        weather = dbModel.weather,
        location = dbModel.location,
    )
}
