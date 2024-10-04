package com.denyskostetskyi.launcher.presentation.service

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Binder
import android.os.Environment
import android.os.IBinder
import android.os.StatFs
import com.denyskostetskyi.launcher.domain.model.SystemInfo

class SystemInfoService : Service() {
    private val batteryManager: BatteryManager by lazy { getSystemService(BatteryManager::class.java) }
    private val activityManager: ActivityManager by lazy { getSystemService(ActivityManager::class.java) }

    private val binder = ServiceBinder()

    inner class ServiceBinder : Binder() {
        fun getSystemInfo(): SystemInfo {
            return fetchSystemInfo()
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    private fun fetchSystemInfo(): SystemInfo {
        val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        val memoryInfo = ActivityManager.MemoryInfo().apply {
            activityManager.getMemoryInfo(this)
        }
        val storageStat = StatFs(Environment.getDataDirectory().path)
        return SystemInfo(
            batteryLevel = batteryLevel,
            availableMemory = memoryInfo.availMem,
            totalMemory = memoryInfo.totalMem,
            availableStorage = storageStat.availableBlocksLong * storageStat.blockSizeLong,
            totalStorage = storageStat.blockCountLong * storageStat.blockSizeLong
        )
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, SystemInfoService::class.java)
    }
}
