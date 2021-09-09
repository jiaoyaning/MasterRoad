package com.jyn.masterroad.view.touch.darg

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.jyn.masterroad.view.draw.dp
import kotlin.math.abs

class DragHelperLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private var dragHelper = ViewDragHelper.create(this, DragCallback())
    private var draggedView = View(context)
    private var minVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

    init {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        draggedView.apply {
            layoutParams = LayoutParams(100f.dp.toInt(), 100f.dp.toInt())
            setBackgroundColor(Color.parseColor("#FA5858"))
        }
        addView(draggedView)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    inner class DragCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child === draggedView //只有指定view才可以拖动
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (releasedChild !== draggedView) return
            if (abs(yvel) > minVelocity) {
                if (yvel > 0) {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                } else {
                    dragHelper.settleCapturedViewAt(0, 0)
                }
            } else {
                if (releasedChild.top < height / 2) {
                    dragHelper.settleCapturedViewAt(0, 0)
                } else {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                }
            }
            postInvalidateOnAnimation()
        }
    }
}