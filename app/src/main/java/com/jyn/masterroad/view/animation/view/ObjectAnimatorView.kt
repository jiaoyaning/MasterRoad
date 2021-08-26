package com.jyn.masterroad.view.animation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.jyn.masterroad.R

class ObjectAnimatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        inflate(getContext(), R.layout.layout_animator_view_property, this)
    }
}