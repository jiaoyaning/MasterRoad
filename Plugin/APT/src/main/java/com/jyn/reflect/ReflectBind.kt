package com.jyn.reflect

import android.app.Activity

/**
 * 通过反射的方式来实现view的绑定
 *  优点：简单
 *  缺点：运行时反射，效率太差
 */
class ReflectBind {
    companion object {
        fun bind(activity: Activity) {
            activity.javaClass.declaredFields.forEach { field ->
                field.getAnnotation(ReflectBindView::class.java)
                    ?.let { field.set(activity, activity.findViewById(it.id)) }
            }
        }
    }
}