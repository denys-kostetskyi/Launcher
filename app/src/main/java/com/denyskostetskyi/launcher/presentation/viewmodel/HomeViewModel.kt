package com.denyskostetskyi.launcher.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.denyskostetskyi.launcher.presentation.state.ClockState
import com.denyskostetskyi.launcher.presentation.state.SystemInfoState
import com.denyskostetskyi.launcher.presentation.state.WeatherForecastState

interface HomeViewModel {
    val clock: LiveData<ClockState>
    val weatherForecast: LiveData<WeatherForecastState>
    val systemInfo: LiveData<SystemInfoState>
}
