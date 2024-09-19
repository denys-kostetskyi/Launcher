package com.denyskostetskyi.launcher.presentation.state

import androidx.annotation.DrawableRes
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.domain.model.SystemInfo
import java.util.Locale

class SystemInfoMapper {
    fun mapToState(systemInfo: SystemInfo): SystemInfoState {
        val batteryLevelIndicatorRes = getBatteryLevelIndicator(systemInfo.batteryLevel)
        return SystemInfoState(
            batteryLevel = systemInfo.batteryLevel,
            batteryLevelIndicatorRes = batteryLevelIndicatorRes,
            availableMemory = formatDouble(systemInfo.availableMemory),
            totalMemory = formatDouble(systemInfo.totalMemory),
            availableStorage = formatDouble(systemInfo.availableStorage),
            totalStorage = formatDouble(systemInfo.totalStorage)
        )
    }

    @DrawableRes
    private fun getBatteryLevelIndicator(batteryLevel: Int) = when {
        batteryLevel == SystemInfo.BATTERY_LEVEL_FULL -> R.drawable.ic_battery_full
        batteryLevel >= SystemInfo.BATTERY_LEVEL_HIGH -> R.drawable.ic_battery_high
        batteryLevel >= SystemInfo.BATTERY_LEVEL_MEDIUM -> R.drawable.ic_battery_medium
        else -> R.drawable.ic_battery_low
    }


    private fun formatDouble(value: Double) = String.format(Locale.getDefault(), "%.1f", value)
}
