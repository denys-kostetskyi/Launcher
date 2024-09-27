package com.denyskostetskyi.launcher.presentation.service

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.IntRange
import com.denyskostetskyi.launcher.data.local.AppDatabase
import com.denyskostetskyi.launcher.data.mapper.WeatherForecastMapper
import com.denyskostetskyi.launcher.data.repository.WeatherForecastRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WeatherForecastJobService : JobService() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onStartJob(params: JobParameters?): Boolean {
        val location = params?.extras?.getString(KEY_LOCATION)
        if (location == null) {
            Log.e(TAG, "Location is null. Job cannot be started.")
            return false
        }
        coroutineScope.launch {
            val dao = AppDatabase.getInstance(applicationContext).weatherForecastDao()
            val mapper = WeatherForecastMapper()
            val repository = WeatherForecastRepositoryImpl(dao, mapper)
            val result = repository.fetchWeatherForecast(location)
            if (result.isSuccess) {
                Log.d(TAG, "Weather data fetched successfully for location: $location")
            } else {
                Log.e(TAG, "Failed to fetch weather data for location: $location")
            }
            jobFinished(params, false)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        coroutineScope.cancel()
        return true
    }

    companion object {
        private const val TAG = "WeatherForecastJob"

        private const val DEFAULT_INTERVAL = 15 * 60 * 1000L
        private const val KEY_LOCATION = "location"
        private const val JOB_ID = 1

        fun newJob(
            context: Context,
            location: String,
            @IntRange(from = DEFAULT_INTERVAL) interval: Long = DEFAULT_INTERVAL
        ): JobInfo {
            val componentName = ComponentName(context, WeatherForecastJobService::class.java)
            val extras = PersistableBundle().apply {
                putString(KEY_LOCATION, location)
            }
            return JobInfo.Builder(JOB_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(interval)
                .setPersisted(true)
                .setExtras(extras)
                .build()
        }
    }
}
