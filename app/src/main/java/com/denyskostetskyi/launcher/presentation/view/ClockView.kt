package com.denyskostetskyi.launcher.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.denyskostetskyi.launcher.databinding.ClockViewLayoutBinding
import java.util.Calendar
import java.util.Locale

class ClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding = ClockViewLayoutBinding.inflate(LayoutInflater.from(context), this)
    private val calendar = Calendar.getInstance()

    init {
        updateClock()
    }

    fun updateClock() {
        calendar.timeInMillis = System.currentTimeMillis()
        binding.textViewTime.text = getTimeString()
        binding.textViewDate.text = getDateString()
    }

    private fun getTimeString(): String {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        return String.format(Locale.getDefault(), TIME_FORMAT, hour, minute, second)
    }

    private fun getDateString(): String {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR) % 100
        return String.format(Locale.getDefault(), DATE_FORMAT, day, month, year)
    }

    companion object {
        private const val TIME_FORMAT = "%02d:%02d:%02d"
        private const val DATE_FORMAT = "%02d.%02d.%02d"
    }
}