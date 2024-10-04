package com.denyskostetskyi.launcher.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.denyskostetskyi.launcher.presentation.state.SystemInfoState
import com.denyskostetskyi.launcher.presentation.state.WeatherForecastState

interface HomeViewModel {
    val weatherForecast: LiveData<WeatherForecastState>
    val systemInfo: LiveData<SystemInfoState>
}
