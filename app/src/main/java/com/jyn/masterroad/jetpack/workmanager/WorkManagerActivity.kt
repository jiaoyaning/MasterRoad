package com.jyn.masterroad.jetpack.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jyn.masterroad.R

/*
 * 使用 WorkManager 处理需要立刻执行的后台任务
 * https://mp.weixin.qq.com/s/rTyBHtULXbCNjBdVuE-VTA
 */
class WorkManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)
    }
}