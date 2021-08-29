package com.jyn.masterroad.view.custom

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.annotation.Keep
import androidx.appcompat.widget.AppCompatEditText
import com.jyn.masterroad.view.draw.dp

/*
 * github
 * https://github.com/rengwuxian/MaterialEditText
 */
@Keep
class MaterialEditText constructor(
    context: Context, attrs: AttributeSet?
) : AppCompatEditText(context, attrs) {
    private var hiltTextSize = 12f.dp //文字大小
    private var hiltTextMargin = 8f.dp //文字与输入框的间隔
    private var floatingLabelShow = false //悬浮提示文字是否正在显示，用来判断是否展示动画

    var floatingLabelFraction = 0f //动画完成度，public的话可以被ObjectAnimator赋值
        set(value) {
            field = value
            invalidate()
        }

    private var animator = ObjectAnimator
        .ofFloat(this, "floatingLabelFraction",  1f)

    private val floatPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = hiltTextSize
    }

    init {
        setPadding(
            paddingLeft,
            (paddingTop + hiltTextSize + hiltTextMargin).toInt(),
            paddingRight,
            paddingBottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        floatPaint.alpha = (floatingLabelFraction * 0xFF).toInt()
        val currentValue = 24f.dp + textSize * (1 - floatingLabelFraction)
        floatPaint.textSize = (12f.dp) + (textSize - 12f.dp) * (1 - floatingLabelFraction)
        canvas.drawText(hint.toString(), 5f.dp, currentValue, floatPaint)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (floatingLabelShow && text.isNullOrEmpty()) {
            //动画隐藏悬浮提示文字
            floatingLabelShow = false
            animator.reverse()
        } else if (!floatingLabelShow && !text.isNullOrEmpty()) {
            //动画显示悬浮提示文字
            floatingLabelShow = true
            animator.start()
        }
    }
}