package com.denyskostetskyi.launcher.presentation.service

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Binder
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.StatFs
import androidx.annotation.IntRange
import com.denyskostetskyi.launcher.domain.model.SystemInfo

class SystemInfoService : Service() {
    private val batteryManager: BatteryManager by lazy { getSystemService(BatteryManager::class.java) }
    private val activityManager: ActivityManager by lazy { getSystemService(ActivityManager::class.java) }

    private val binder = ServiceBinder()

    private var handlerThread: HandlerThread? = null
    private var handler: Handler? = null

    @Volatile
    private var isBound = false
    private var callback: Callback? = null
    private var delay: Long = 0L

    private val systemInfoRunnable = object : Runnable {
        override fun run() {
            callback?.let {
                val systemInfo = fetchSystemInfo()
                it.onSystemInfoChanged(systemInfo)
            }
            handler?.postDelayed(this, delay)
        }
    }

    inner class ServiceBinder : Binder() {
        fun setCallback(@IntRange(from = 1L) delay: Long, callback: Callback): Boolean {
            return if (this@SystemInfoService.callback == null) {
                this@SystemInfoService.delay = delay
                this@SystemInfoService.callback = callback
                startSystemInfoUpdates()
                true
            } else {
                false
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        synchronized(this) {
            return if (isBound) {
                null
            } else {
                isBound = true
                binder
            }
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stopSystemInfoUpdates()
        isBound = false
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        stopSystemInfoUpdates()
        super.onDestroy()
    }

    private fun startSystemInfoUpdates() {
        if (handlerThread == null || handler == null) {
            handlerThread = HandlerThread(THREAD_NAME).apply { start() }
            handler = Handler(handlerThread!!.looper).apply {
                post(systemInfoRunnable)
            }
        }
    }

    private fun stopSystemInfoUpdates() {
        handler?.removeCallbacksAndMessages(null)
        handlerThread?.quitSafely()
        handlerThread = null
        handler = null
        callback = null
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

    fun interface Callback {
        fun onSystemInfoChanged(systemInfo: SystemInfo)
    }

    companion object {
        private const val THREAD_NAME = "SystemInfoServiceThread"

        fun newIntent(context: Context): Intent = Intent(context, SystemInfoService::class.java)
    }
}
