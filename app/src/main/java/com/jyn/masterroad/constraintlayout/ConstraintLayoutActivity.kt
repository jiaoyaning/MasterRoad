package com.jyn.masterroad.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.masterroad.R
import com.jyn.masterroad.base.RoutePath

/**
 * https://juejin.cn/post/6854573221312725000
 *
 * http://www.flyou.ren/2019/10/10/ConstraintLayout/
 */
@Route(path = RoutePath.ConstraintLayout.path)
class ConstraintLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraintlayout)
    }
}