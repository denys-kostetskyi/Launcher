package com.denyskostetskyi.launcher.presentation.state

import androidx.annotation.DrawableRes

data class SystemInfoState(
    val batteryLevel: Int,
    @DrawableRes val batteryLevelIndicatorRes: Int,
    val availableMemory: String,
    val totalMemory: String,
    val availableStorage: String,
    val totalStorage: String,
)
