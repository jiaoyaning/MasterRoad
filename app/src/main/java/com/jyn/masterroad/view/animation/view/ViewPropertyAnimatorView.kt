package com.jyn.masterroad.view.animation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.dp

class ViewPropertyAnimatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(getContext(), R.layout.layout_animator_view_property, this)
        findViewById<ImageView>(R.id.animate_iv1).setOnClickListener(::viewAnimate)
    }

    private fun viewAnimate(view: View) {
        Toast.makeText(view.context, "点击", Toast.LENGTH_SHORT).show()
        view.animate()
            .scaleX(2f)
            .scaleY(2f)
            .translationY(100f.dp)
            .translationX(200f.dp)
    }
}