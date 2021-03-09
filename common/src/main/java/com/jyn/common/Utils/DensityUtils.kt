package com.jyn.common.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

@SuppressLint("StaticFieldLeak")
var context: Context? = null

fun dp2px(dpVal: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dpVal, context?.resources?.displayMetrics).toInt()
}

fun px2dp(pxVal: Float): Float {
    val scale = context?.resources?.displayMetrics?.density
    return if (scale != null) pxVal / scale else pxVal
}

fun sp2px(spVal: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            spVal, context?.resources?.displayMetrics).toInt()
}

fun px2sp(pxVal: Float): Float {
    val scale = context?.resources?.displayMetrics?.scaledDensity;
    return if (scale != null) pxVal / scale else pxVal
}

/*
 * 获取屏幕宽高
 */
fun getScreenWidth(): Int? = context?.resources?.displayMetrics?.widthPixels
fun getScreenHeight(): Int? = context?.resources?.displayMetrics?.heightPixels