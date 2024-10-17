package com.denyskostetskyi.launcher.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.denyskostetskyi.launcher.R

class AppItemViewHolder(itemView: View) : ViewHolder(itemView) {
    val imageViewAppIcon: ImageView = itemView.findViewById(R.id.imageViewAppIcon)
    val textViewAppName: TextView = itemView.findViewById(R.id.textViewAppName)
}
