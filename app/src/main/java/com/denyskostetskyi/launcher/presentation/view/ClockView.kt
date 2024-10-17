package com.denyskostetskyi.launcher.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.presentation.state.ClockState

class ClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val textViewTime: TextView
    private val textViewDate: TextView

    init {
        View.inflate(context, R.layout.clock_view_layout, this)
        textViewTime = findViewById(R.id.textViewTime)
        textViewDate = findViewById(R.id.textViewDate)
    }

    fun updateState(state: ClockState) {
        textViewTime.text = state.time
        textViewDate.text = state.date
    }
}
