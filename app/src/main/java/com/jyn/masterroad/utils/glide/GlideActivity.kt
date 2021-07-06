package com.jyn.masterroad.utils.glide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jyn.masterroad.R

/**
 * 面试官：简历上最好不要写Glide，不是问源码那么简单
 * https://juejin.cn/post/6844903986412126216
 *
 * 【带着问题学】Glide做了哪些优化?
 * https://juejin.cn/post/6970683481127043085
 *
 * Android NDK —— GIFLIB 优化 Glide GIF 的加载
 * https://sharrychoo.github.io/blog/android-source/graphic-choreographer
 */
class GlideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)
    }
}