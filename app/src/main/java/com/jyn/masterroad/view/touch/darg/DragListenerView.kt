package com.jyn.masterroad.view.touch.darg

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.jyn.masterroad.view.touch.darg.DragHelperView.Companion.COLUMNS
import com.jyn.masterroad.view.touch.darg.DragHelperView.Companion.ROWS

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
     * [startDrag] 参数解析
     *   ClipData：拖拽时用于传递的数据，会在 DragEvent.ACTION_DROP 拖拽结束松手时才能获取到 ClipData 的数据
     *   DragShadowBuilder：自定义在拖拽时生成View样式，默认为半透明，可以观察跟随手指的拖拽状态
     *   myLocalState：可以用它传递本地数据，监听 DragEvent.ACTION_DRAG_STARTED、DragEvent.ACTION_DRAG_ENTERED、DragEvent.ACTION_DRAG_ENDED
     *                 等拖拽状态时通过 DragEvent.getLocalState() 随时获取该本地数据。但需要注意的是，如果是跨进程的Activity之间通信，DragEvent.getLocalState() 会返回null
     *   flags：控制拖拽时的操作，一般传递0即可
     *
     *
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
        //开启自定义绘制顺序

        isChildrenDrawingOrderEnabled = true
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#FF00FF")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#0080FF")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#00FFFF")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#80FF00")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#F4FA58")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#FA5858")) })
    }

    //滑动监听
    private val dragListener = DragListener()

    //当前选中view
    private var draggedView: View? = null

    //子view列表
    private val childList = mutableListOf<View>()

    /**
     * XML加载完成后的回调
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        for (child in children) {
            childList.add(child)
            child.setOnLongClickListener {
                draggedView = it
                it.startDrag(null, DragShadowBuilder(it), it, 0)
                false
            }
            child.setOnDragListener(dragListener)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth / DragHelperView.COLUMNS
        val childHeight = specHeight / DragHelperView.ROWS
        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft: Int
        var childTop: Int
        val childWidth = width / DragHelperView.COLUMNS
        val childHeight = height / DragHelperView.ROWS
        for ((index, child) in children.withIndex()) {
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
        }
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
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> if (event.localState === v) {
                    v.visibility = View.INVISIBLE
                }
                DragEvent.ACTION_DRAG_ENTERED -> if (event.localState !== v) {
                    sort(v)
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                }
                DragEvent.ACTION_DRAG_ENDED -> if (event.localState === v) {
                    v.visibility = View.VISIBLE
                }
            }
            return true
        }

        private fun sort(targetView: View) {
            var draggedIndex = -1
            var targetIndex = -1
            for ((index, child) in childList.withIndex()) {
                if (targetView === child) {
                    targetIndex = index
                } else if (draggedView === child) {
                    draggedIndex = index
                }
            }
            childList.removeAt(draggedIndex)
            childList.add(targetIndex, draggedView!!)
            var childLeft: Int
            var childTop: Int
            val childWidth = width / COLUMNS
            val childHeight = height / ROWS
            for ((index, child) in childList.withIndex()) {
                childLeft = index % 2 * childWidth
                childTop = index / 2 * childHeight
                child.animate()
                    .translationX(childLeft.toFloat())
                    .translationY(childTop.toFloat())
                    .setDuration(150)
            }
        }
    }
}