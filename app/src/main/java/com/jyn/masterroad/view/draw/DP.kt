package com.jyn.masterroad.view.draw

import android.content.res.Resources
import android.util.TypedValue

fun dp2px(value: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        Resources.getSystem().displayMetrics //getSystem只能读取到系统资源
    )
}

val Float.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics //getSystem只能读取到系统资源
    )