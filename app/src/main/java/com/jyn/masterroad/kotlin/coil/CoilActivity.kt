package com.jyn.masterroad.kotlin.coil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jyn.masterroad.R

/**
 * 还在用 Glide？看看 Google 官推的图片库 Coil 有何不同！
 * https://mp.weixin.qq.com/s/QaNPjuVLybwk92bqPSyAZA
 */
class CoilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coil)
    }
}