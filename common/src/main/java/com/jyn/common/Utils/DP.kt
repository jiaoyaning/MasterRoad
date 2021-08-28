package com.jyn.masterroad.view.draw

import android.content.res.Resources
import android.util.TypedValue

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics //getSystem只能读取到系统资源
    )