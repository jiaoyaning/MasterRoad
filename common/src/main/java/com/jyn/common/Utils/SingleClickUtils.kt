package com.jyn.common.Utils

import android.app.Activity
import android.content.ContextWrapper
import android.os.SystemClock
import android.view.View

/**
 * Android 优雅处理重复点击
 * https://mp.weixin.qq.com/s/_ZlnAkIaHwbWG_ypGqn1Eg
 */
const val single_click_millis = 10000

fun View.onSingleClick(
    interval: Int = 1000,
    isShareSingleClick: Boolean = true,
    listener: (View) -> Unit
) {
    setOnClickListener {
        val target = if (isShareSingleClick) getActivity(this)?.window?.decorView ?: this else this
        val millis = target.getTag(single_click_millis) as? Long ?: 0
        if (SystemClock.uptimeMillis() - millis >= interval) {
            target.setTag(single_click_millis, SystemClock.uptimeMillis())
            listener.invoke(this)
        }
    }
}

private fun getActivity(view: View): Activity? {
    var context = view.context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}