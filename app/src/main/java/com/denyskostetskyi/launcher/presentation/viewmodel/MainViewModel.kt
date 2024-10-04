package com.denyskostetskyi.launcher.presentation.viewmodel

import com.denyskostetskyi.launcher.domain.model.AppItem
import com.denyskostetskyi.launcher.domain.model.SystemInfo
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast
import java.util.Calendar

interface MainViewModel {

    fun updateClock(calendar: Calendar)

    fun updateWeatherForecast(weatherForecast: HourlyWeatherForecast)

    fun updateSystemInfo(systemInfo: SystemInfo)

    fun updateAppList(appItemList: List<AppItem>)
}
