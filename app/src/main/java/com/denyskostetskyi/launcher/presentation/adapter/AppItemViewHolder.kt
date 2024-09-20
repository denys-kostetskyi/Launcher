package com.denyskostetskyi.launcher.presentation.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.denyskostetskyi.launcher.databinding.ItemAppBinding
import com.denyskostetskyi.launcher.presentation.state.AppItemState

class AppItemViewHolder(val binding: ItemAppBinding) : ViewHolder(binding.root) {
    fun bind(state: AppItemState) {
        with(binding) {
            imageViewAppIcon.setImageDrawable(state.appIcon)
            textViewAppName.text = state.appName
        }
    }
}
