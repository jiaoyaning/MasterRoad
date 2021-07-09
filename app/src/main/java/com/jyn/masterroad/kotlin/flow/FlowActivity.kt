package com.jyn.masterroad.kotlin.flow

import androidx.activity.viewModels
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityFlowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/*
 * Kotlin Coroutines Flow 系列(一) Flow 基本使用
 * https://juejin.cn/post/6844904057530908679
 *
 * Android 上的 Kotlin 数据流
 * https://developer.android.com/kotlin/flow?hl=zh-cn
 *
 * Kotlin Flow 场景化学习
 * https://mp.weixin.qq.com/s/XfJ4Gkp_9Ww5Dxx8Di_AVg
 *
 * 协程 Flow 最佳实践 | 基于 Android 开发者峰会应用
 * https://mp.weixin.qq.com/s/YttebOHPLd3LR7rAuOYshg
 *
 * 从 LiveData 迁移到 Kotlin 数据流（Android 开发者）
 * https://mp.weixin.qq.com/s/o61NDIptP94X4HspKwiR2w
 */
class FlowActivity : BaseActivity<ActivityFlowBinding>(R.layout.activity_flow),
    CoroutineScope by MainScope() {
    val flowTest: FlowTest by viewModels()

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}