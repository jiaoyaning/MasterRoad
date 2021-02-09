package com.jyn.common.ARouter

import com.alibaba.android.arouter.launcher.ARouter

fun goto(path: String) {
    ARouter.getInstance().build(path).navigation()
}