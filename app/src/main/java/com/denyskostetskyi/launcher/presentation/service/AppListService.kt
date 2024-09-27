package com.denyskostetskyi.launcher.presentation.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Build
import com.denyskostetskyi.launcher.domain.model.AppItem

class AppListService : Service() {
    private val binder = ServiceBinder()

    inner class ServiceBinder : Binder() {
        val appList get() = getAppList()
    }

    override fun onBind(intent: Intent?) = binder

    private fun getAppList(): List<AppItem> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resolvedInfoList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(
                mainIntent,
                PackageManager.ResolveInfoFlags.of(0L)
            )
        } else {
            packageManager.queryIntentActivities(mainIntent, 0)
        }
        return resolvedInfoList.map { info ->
            AppItem(
                appName = info.loadLabel(packageManager).toString(),
                activityName = info.activityInfo.name,
                packageName = info.activityInfo.packageName
            )
        }
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, AppListService::class.java)
    }
}
