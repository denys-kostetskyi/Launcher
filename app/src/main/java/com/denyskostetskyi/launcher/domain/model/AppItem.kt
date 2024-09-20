package com.denyskostetskyi.launcher.domain.model

import android.graphics.drawable.Drawable

data class AppItem(
    val appIcon: Drawable,
    val appName: String,
    val packageName: String,
)
