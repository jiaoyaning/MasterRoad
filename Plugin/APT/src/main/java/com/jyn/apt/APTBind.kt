package com.jyn.apt

import android.app.Activity

/**
 * apt 实现主要用两步
 *   1. 生成所需要的代码
 *   2. 使用第一步所生成的代码
 */
class APTBind {
    companion object {
        //此为第2步，使用所生成的代码

        fun bind(activity: Activity) {
            //找到所生成的代码
            val bindingClass = Class.forName("${activity.javaClass.canonicalName}Binding")
            //获取所生成代码中，带activity参数的构造方法
            val constructor = bindingClass?.getDeclaredConstructor(activity.javaClass)
            //根据构造方法初始化对象
            constructor?.newInstance(activity)
        }
    }
}