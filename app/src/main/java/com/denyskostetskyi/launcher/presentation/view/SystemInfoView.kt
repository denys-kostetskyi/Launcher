package com.denyskostetskyi.launcher.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.presentation.state.SystemInfoState

class SystemInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val textViewBatteryLevel: TextView
    private val imageViewBatteryLevel: ImageView
    private val textViewAvailableMemory: TextView
    private val textViewTotalMemory: TextView
    private val textViewAvailableStorage: TextView
    private val textViewTotalStorage: TextView

    init {
        View.inflate(context, R.layout.system_info_view_layout, this)
        textViewBatteryLevel = findViewById(R.id.textViewBatteryLevel)
        imageViewBatteryLevel = findViewById(R.id.imageViewBatteryLevel)
        textViewAvailableMemory = findViewById(R.id.textViewAvailableMemory)
        textViewTotalMemory = findViewById(R.id.textViewTotalMemory)
        textViewAvailableStorage = findViewById(R.id.textViewAvailableStorage)
        textViewTotalStorage = findViewById(R.id.textViewTotalStorage)
    }

    fun updateState(state: SystemInfoState) {
        val batteryLevel = context.getString(R.string.battery_level, state.batteryLevel)
        val batteryDrawable = ContextCompat.getDrawable(context, state.batteryLevelIndicatorRes)
        val availableMemory = context.getString(R.string.available_memory, state.availableMemory)
        val totalMemory = context.getString(R.string.total_memory, state.totalMemory)
        val availableStorage = context.getString(R.string.available_storage, state.availableStorage)
        val totalStorage = context.getString(R.string.total_storage, state.totalStorage)
        textViewBatteryLevel.text = batteryLevel
        imageViewBatteryLevel.setImageDrawable(batteryDrawable)
        textViewAvailableMemory.text = availableMemory
        textViewTotalMemory.text = totalMemory
        textViewAvailableStorage.text = availableStorage
        textViewTotalStorage.text = totalStorage
    }
}
