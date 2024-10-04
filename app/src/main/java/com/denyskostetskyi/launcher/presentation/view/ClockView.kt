package com.denyskostetskyi.launcher.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.denyskostetskyi.launcher.databinding.ClockViewLayoutBinding
import com.denyskostetskyi.launcher.presentation.state.ClockState

class ClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding = ClockViewLayoutBinding.inflate(LayoutInflater.from(context), this)

    fun updateState(state: ClockState) {
        with(binding) {
            textViewTime.text = state.time
            textViewDate.text = state.date
        }
    }
}
