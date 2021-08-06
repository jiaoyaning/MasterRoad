package com.jyn.masterroad.kotlin.flow

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.common.Base.BaseScopeActivity
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
 * Kotlin Flow 场景化学习
 * https://mp.weixin.qq.com/s/XfJ4Gkp_9Ww5Dxx8Di_AVg
 *
 * 协程 Flow 最佳实践 | 基于 Android 开发者峰会应用
 * https://mp.weixin.qq.com/s/YttebOHPLd3LR7rAuOYshg
 *
 * 从 LiveData 迁移到 Kotlin 数据流（Android 开发者）
 * https://mp.weixin.qq.com/s/o61NDIptP94X4HspKwiR2w
 *
 * 官方推荐 Flow 取代 LiveData,有必要吗？
 * https://juejin.cn/post/6986265488275800072
 *
 * Kotlin Flow 操作符：篇幅很大 你忍一下
 * https://mp.weixin.qq.com/s/-G5AsB5iSwL3BoERJPlELw
 */
@Route(path = RoutePath.Flow.path)
class FlowActivity : BaseScopeActivity<ActivityFlowBinding>(R.layout.activity_flow) {
    private val flowCreate: FlowCreate by viewModels()
    private val sharedAndStateFlow: SharedAndStateFlow by viewModels()

    override fun initData() {
        binding.flowCreate = flowCreate
        binding.sharedAndStateFlow = sharedAndStateFlow
    }
}