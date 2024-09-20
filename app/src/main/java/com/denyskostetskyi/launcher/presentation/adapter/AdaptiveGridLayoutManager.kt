// https://gist.github.com/omidraha/af3aa017d4ec06342bdc03c49d4b83b1
package com.denyskostetskyi.launcher.presentation.adapter

import android.content.Context
import androidx.annotation.FloatRange
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class AdaptiveGridLayoutManager(
    context: Context,
    @FloatRange(from = 0.0, fromInclusive = false) private val columnWidth: Float,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
) : GridLayoutManager(context, 1, orientation, reverseLayout) {
    private var isSpanCountSet = false

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (!isSpanCountSet) {
            updateSpanCount()
        }
        super.onLayoutChildren(recycler, state)
    }

    private fun updateSpanCount() {
        val totalSpace = if (orientation == VERTICAL) {
            width - paddingRight - paddingLeft
        } else {
            height - paddingTop - paddingBottom
        }
        val newSpanCount = max(1, (totalSpace / columnWidth).toInt())
        setSpanCount(newSpanCount)
    }
}
