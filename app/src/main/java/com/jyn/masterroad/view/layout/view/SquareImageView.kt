package com.jyn.masterroad.view.layout.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * Created by jiaoyaning on 2021/8/29.
 */
class SquareImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        measuredHeight //自己测量的期望尺寸
        width   //实际尺寸，因为可能会被父view修改掉

        val size = min(measuredWidth, measuredHeight)
        /**
         * 之所以要用 setMeasuredDimension 是因为要保存 measuredWidth 和 measuredHeight
         *  其可能会被多个地方使用到，而不仅限于本方法
         */
        setMeasuredDimension(size, size)
    }

    /**
     * 只改layout的话，会导致父view获取到错误的尺寸
     */
    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        val width = r - l //view的宽度
        val height = b - t//view的高度
        val size = min(width, height)
        super.layout(l, t, l + size, t + size)
    }
}