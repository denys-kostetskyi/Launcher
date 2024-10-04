package com.denyskostetskyi.launcher.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.denyskostetskyi.launcher.domain.model.AppItem
import com.denyskostetskyi.launcher.domain.model.SystemInfo
import com.denyskostetskyi.launcher.presentation.state.SystemInfoMapper
import com.denyskostetskyi.launcher.presentation.state.SystemInfoState
import com.denyskostetskyi.launcher.presentation.state.WeatherForecastMapper
import com.denyskostetskyi.launcher.presentation.state.WeatherForecastState
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast

class SharedViewModel : ViewModel(), MainViewModel, HomeViewModel, AppListViewModel {
    private val weatherForecastMapper = WeatherForecastMapper()
    private val systemInfoMapper = SystemInfoMapper()

    private val _weatherForecast = MutableLiveData<WeatherForecastState>()
    override val weatherForecast: LiveData<WeatherForecastState>
        get() = _weatherForecast

    private val _systemInfo = MutableLiveData<SystemInfoState>()
    override val systemInfo: LiveData<SystemInfoState>
        get() = _systemInfo

    private val _appList = MutableLiveData<List<AppItem>>()
    override val appList: LiveData<List<AppItem>>
        get() = _appList

    override fun updateWeatherForecast(weatherForecast: HourlyWeatherForecast) {
        val state = weatherForecastMapper.mapToState(weatherForecast)
        _weatherForecast.postValue(state)
    }

    override fun updateSystemInfo(systemInfo: SystemInfo) {
        val state = systemInfoMapper.mapToState(systemInfo)
        _systemInfo.postValue(state)
    }

    override fun updateAppList(appItemList: List<AppItem>) {
        _appList.postValue(appItemList)
    }
}
