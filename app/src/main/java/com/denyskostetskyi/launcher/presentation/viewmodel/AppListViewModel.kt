package com.denyskostetskyi.launcher.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.denyskostetskyi.launcher.domain.model.AppItem

interface AppListViewModel {
    val appList: LiveData<List<AppItem>>
}
