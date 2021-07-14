package com.jyn.masterroad.base.jvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jyn.masterroad.R

/*
 * 深入理解Android中的ClassLoader
 * https://juejin.cn/post/6844903520815022093
 *
 * https://mp.weixin.qq.com/s/d5F0B7CkephY_rSMKoWrdw
 */
class ClassLoaderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_loader)
    }
}