package com.denyskostetskyi.launcher.presentation.activity

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.denyskostetskyi.launcher.databinding.ActivityMainBinding
import com.denyskostetskyi.launcher.presentation.service.AppListService
import com.denyskostetskyi.launcher.presentation.service.SystemInfoService
import com.denyskostetskyi.launcher.presentation.viewmodel.MainViewModel
import com.denyskostetskyi.launcher.presentation.viewmodel.SharedViewModel
import com.denyskostetskyi.weatherforecast.library.IWeatherForecastService
import com.denyskostetskyi.weatherforecast.library.WeatherForecastServiceHelper
import com.denyskostetskyi.weatherforecast.library.domain.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private var weatherForecastServiceBinder: IWeatherForecastService? = null
    private val weatherForecastServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            weatherForecastServiceBinder = IWeatherForecastService.Stub.asInterface(service)
            Log.d(TAG, "Bound to ${name?.className}")
            scheduleWeatherForecastUpdate()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            weatherForecastServiceBinder = null
        }
    }

    private var systemInfoServiceBinder: SystemInfoService.ServiceBinder? = null
    private val systemInfoServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            systemInfoServiceBinder = service as SystemInfoService.ServiceBinder
            Log.d(TAG, "Bound to ${name?.className}")
            scheduleSystemInfoUpdate()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            systemInfoServiceBinder = null
        }
    }

    private val appListChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateAppList()
        }
    }
    private var appListServiceBinder: AppListService.ServiceBinder? = null
    private val appListServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            appListServiceBinder = service as AppListService.ServiceBinder
            Log.d(TAG, "Bound to ${name?.className}")
            updateAppList()
            registerAppListChangeReceiver()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            appListServiceBinder = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        scheduleClockUpdate()
        bindWeatherForecastService()
        bindSystemInfoService()
        bindAppListService()
    }

    private fun bindWeatherForecastService() {
        val intent = WeatherForecastServiceHelper.newIntent()
        val result = bindService(intent, weatherForecastServiceConnection, Context.BIND_AUTO_CREATE)
        if (!result) {
            Log.d(TAG, "WeatherForecastService bind failed")
        }
    }

    private fun bindSystemInfoService() {
        val intent = SystemInfoService.newIntent(this)
        val result = bindService(intent, systemInfoServiceConnection, Context.BIND_AUTO_CREATE)
        if (!result) {
            Log.d(TAG, "SystemInfoService bind failed")
        }
    }

    private fun bindAppListService() {
        val intent = AppListService.newIntent(this)
        val result = bindService(intent, appListServiceConnection, Context.BIND_AUTO_CREATE)
        if (!result) {
            Log.d(TAG, "AppListService bind failed")
        }
    }

    private fun registerAppListChangeReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addAction(Intent.ACTION_PACKAGE_CHANGED)
            addDataScheme(DATA_SCHEME_PACKAGE)
        }
        registerReceiver(appListChangeReceiver, intentFilter)
    }

    private fun scheduleTask(coroutineContext: CoroutineContext, delay: Long, task: () -> Unit) {
        lifecycleScope.launch(coroutineContext) {
            while (isActive) {
                task()
                delay(delay)
            }
        }
    }

    private fun scheduleClockUpdate() {
        scheduleTask(Dispatchers.Default, CLOCK_UPDATE_DELAY) {
            viewModel.updateClock()
        }
    }

    private fun scheduleWeatherForecastUpdate() {
        scheduleTask(Dispatchers.IO, WEATHER_FORECAST_UPDATE_DELAY) {
            weatherForecastServiceBinder?.getHourlyWeatherForecast(
                weatherForecastLocation,
                WeatherForecastServiceHelper.getTimeString()
            )?.let {
                viewModel.updateWeatherForecast(it)
            }
        }
    }

    private fun scheduleSystemInfoUpdate() {
        scheduleTask(Dispatchers.Default, SYSTEM_INFO_UPDATE_DELAY) {
            systemInfoServiceBinder?.getSystemInfo()?.let {
                viewModel.updateSystemInfo(it)
            }
        }
    }

    private fun updateAppList() {
        lifecycleScope.launch {
            appListServiceBinder?.appList?.let {
                viewModel.updateAppList(it)
            }
        }
    }

    override fun onDestroy() {
        unbindService(weatherForecastServiceConnection)
        unbindService(systemInfoServiceConnection)
        unbindService(appListServiceConnection)
        unregisterReceiver(appListChangeReceiver)
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val DATA_SCHEME_PACKAGE = "package"
        private const val CLOCK_UPDATE_DELAY = 1000L
        private const val WEATHER_FORECAST_UPDATE_DELAY = 60 * 60 * 1000L
        private const val SYSTEM_INFO_UPDATE_DELAY = 10 * 60 * 1000L

        private val weatherForecastLocation =
            Location(name = "Lviv", latitude = 49.8397, longitude = 24.0297)
    }
}
