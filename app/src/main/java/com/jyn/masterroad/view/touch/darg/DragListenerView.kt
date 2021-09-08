package com.jyn.masterroad.view.touch.darg

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

/*
 * Android控制View绘制顺序的关键方法——setChildrenDrawingOrderEnabled
 * https://blog.csdn.net/jdsjlzx/article/details/107375540
 *
 * 源码：https://github.com/rengwuxian/HenCoderPlus7/blob/main/CustomViewTouchDrag/app/src/main/java/com/hencoder/drag/view/DragListenerGridView.kt
 */
class DragListenerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {
    /**
     *  OnDragListener 使用步骤
     *      1. View.startDrag() 开始拖拽
     *      2. 实现OnDragListener接口，并重写onDrag方法
     *      3. View.setOnDragListener() 添加拖拽监听
     *
     *  如何自定义子view绘制顺序？
     *      1. 调用 setChildrenDrawingOrderEnable(true) 开启自定义绘制顺序
     *      2. 重写 getChildDrawingOrder() 修改 View 的取值索引
     *      PS 用setZ(float),自定义Z值，值越大越优先绘制
     */
    companion object {
        const val TAG = "Drag"
    }

    init {
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#FF00FF")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#0080FF")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#00FFFF")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#80FF00")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#F4FA58")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#FA5858")) })
        isChildrenDrawingOrderEnabled = true
    }

    //滑动监听
    val dragListener = DragListener()

    //子view列表
    val childList = mutableListOf<View>()

    /**
     * XML加载完成后的回调
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        for (child in children) {
            childList.add(child)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    override fun onDragEvent(event: DragEvent): Boolean {
        return super.onDragEvent(event)
    }

    inner class DragListener : OnDragListener {
        /**
         * 所有的子view都会收到拖拽事件，相当于通知ViewGroup有一个view被拖拽了
         *
         * View         被拖拽的View对象
         * DragEvent    View的拖拽状态，比较常用的有以下几种状态：
         *      DragEvent.ACTION_DRAG_STARTED   开始拖拽，在调用 view.startDrag() 时回调
         *      DragEvent.ACTION_DRAG_ENTERED   当拖拽触摸到了被拖拽的那个View的区域内就会回调
         *      DragEvent.ACTION_DRAG_ENDED     已经松手结束拖拽
         *      DragEvent.ACTION_DROP           拖拽结束松手了
         *
         *  return      true为处理该拖拽事件，false则表示不处理
         *              注意：false情况下将触发view的 onDragEvent() 方法
         */
        override fun onDrag(v: View, event: DragEvent): Boolean {
            return true
        }
    }
}