package com.denyskostetskyi.launcher.presentation.view

import android.content.Context
import android.text.format.DateFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.denyskostetskyi.launcher.databinding.ClockViewLayoutBinding
import java.util.Calendar

class ClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding = ClockViewLayoutBinding.inflate(LayoutInflater.from(context), this)
    private val calendar = Calendar.getInstance()
    private val time get() = DateFormat.format(TIME_FORMAT, calendar)
    private val date get() = DateFormat.format(DATE_FORMAT, calendar)

    init {
        updateClock()
    }

    fun updateClock() {
        binding.textViewTime.text = time
        binding.textViewDate.text = date
    }

    companion object {
        private const val TIME_FORMAT = "HH:mm:ss"
        private const val DATE_FORMAT = "dd.MM.yyyy"
    }
}
