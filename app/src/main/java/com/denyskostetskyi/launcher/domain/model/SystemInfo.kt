package com.denyskostetskyi.launcher.domain.model

data class SystemInfo(
    val batteryLevel: Int,
    val availableMemory: Long,
    val totalMemory: Long,
    val availableStorage: Long,
    val totalStorage: Long,
) {
    companion object {
        const val BATTERY_LEVEL_FULL = 100
        const val BATTERY_LEVEL_HIGH = 80
        const val BATTERY_LEVEL_MEDIUM = 50
        const val BATTERY_LEVEL_LOW = 20
    }
}
