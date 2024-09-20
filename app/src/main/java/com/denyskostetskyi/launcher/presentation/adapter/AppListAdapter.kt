package com.denyskostetskyi.launcher.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.denyskostetskyi.launcher.databinding.ItemAppBinding
import com.denyskostetskyi.launcher.domain.model.AppItem
import com.denyskostetskyi.launcher.presentation.state.AppItemState

class AppListAdapter : ListAdapter<AppItem, AppItemViewHolder>(AppItemDiffCallback()) {
    var onAppClicked: ((AppItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppItemViewHolder {
        val binding = ItemAppBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppItemViewHolder, position: Int) {
        val item = currentList[position]
        val state = AppItemState(
            appIcon = item.appIcon,
            appName = item.appName
        )
        holder.bind(state)
        holder.binding.imageViewAppIcon.setOnClickListener {
            onAppClicked?.invoke(item)
        }
    }
}
