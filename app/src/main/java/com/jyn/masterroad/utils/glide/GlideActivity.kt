package com.jyn.masterroad.utils.glide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jyn.masterroad.R

/**
 * 面试官：简历上最好不要写Glide，不是问源码那么简单
 * https://juejin.cn/post/6844903986412126216
 */
class GlideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)
    }
}