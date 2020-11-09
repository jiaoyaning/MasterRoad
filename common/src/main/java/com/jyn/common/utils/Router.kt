package com.jyn.common.utils

import com.alibaba.android.arouter.launcher.ARouter

object Router {
    fun goto(path: String) {
        ARouter.getInstance().build(path).navigation()
    }
}