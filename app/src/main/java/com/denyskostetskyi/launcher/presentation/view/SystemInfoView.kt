package com.denyskostetskyi.launcher.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.databinding.SystemInfoViewLayoutBinding
import com.denyskostetskyi.launcher.presentation.state.SystemInfoState

class SystemInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val binding =
        SystemInfoViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun updateState(state: SystemInfoState) {
        val batteryDrawable = ContextCompat.getDrawable(context, state.batteryLevelIndicatorRes)
        with(binding) {
            textViewBatteryLevel.text =
                context.getString(R.string.battery_level, state.batteryLevel)
            imageViewBatteryLevel.setImageDrawable(batteryDrawable)
            textViewAvailableMemory.text =
                context.getString(R.string.available_memory, state.availableMemory)
            textViewTotalMemory.text = context.getString(R.string.total_memory, state.totalMemory)
            textViewAvailableStorage.text =
                context.getString(R.string.available_storage, state.availableStorage)
            textViewTotalStorage.text =
                context.getString(R.string.total_storage, state.totalStorage)
        }
    }
}
