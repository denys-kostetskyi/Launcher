package com.denyskostetskyi.launcher.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.denyskostetskyi.launcher.domain.model.AppItem

class AppItemDiffCallback : DiffUtil.ItemCallback<AppItem>() {
    override fun areItemsTheSame(oldItem: AppItem, newItem: AppItem): Boolean {
        return oldItem.packageName == newItem.packageName
    }

    override fun areContentsTheSame(oldItem: AppItem, newItem: AppItem): Boolean {
        return oldItem == newItem
    }
}
