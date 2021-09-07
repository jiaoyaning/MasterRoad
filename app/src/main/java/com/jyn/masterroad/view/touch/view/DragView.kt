package com.jyn.masterroad.view.touch.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.jyn.masterroad.R

/**
 *
 */
class DragView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {
    init {
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#EF5350")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#9C27B0")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#1E88E5")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#00695C")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#00695C")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#546E7A")) })
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }
}