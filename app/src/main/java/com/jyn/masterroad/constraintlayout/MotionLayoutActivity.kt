package com.jyn.masterroad.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.masterroad.R
import com.jyn.masterroad.base.RoutePath
import kotlinx.android.synthetic.main.activity_motion_layout.*

/**
 * 官方文档：
 * https://developer.android.google.cn/training/constraint-layout/motionlayout?hl=zh_cn
 *
 * 官方实例：
 * https://developer.android.google.cn/training/constraint-layout/motionlayout/examples?hl=zh_cn
 *
 * https://juejin.cn/post/6854573206653812743
 *
 * https://juejin.cn/post/6844903918598635534
 */
@Route(path = RoutePath.MotionLayout.path)
class MotionLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)
        val constraintSet = motionLayout.getConstraintSet(R.id.end)
        constraintSet.setRotation(R.id.ball, 180f)
        motionLayout.updateState(R.id.end, constraintSet)
    }
}