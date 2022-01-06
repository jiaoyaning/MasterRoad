package com.jyn.masterroad.kotlin.flow

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.common.Base.BaseScopeActivity
import com.jyn.masterroad.R
import com.jyn.masterroad.databinding.ActivityFlowBinding

/*
 * 官方文档
 * https://developer.android.google.cn/kotlin/flow?hl=zh_cn
 * 学习采用 Kotlin Flow 和 LiveData 的高级协程
 * https://developer.android.com/codelabs/advanced-kotlin-coroutines
 *
 * Kotlin Flow 场景化学习
 * https://mp.weixin.qq.com/s/XfJ4Gkp_9Ww5Dxx8Di_AVg
 *
 * 从 LiveData 迁移到 Kotlin 数据流（Android 开发者）
 * https://mp.weixin.qq.com/s/o61NDIptP94X4HspKwiR2w
 *
 * 官方推荐 Flow 取代 LiveData,有必要吗？
 * https://juejin.cn/post/6986265488275800072
 *
 * Kotlin Flow 操作符：篇幅很大 你忍一下
 * https://mp.weixin.qq.com/s/-G5AsB5iSwL3BoERJPlELw
 *
 *【Kotlin Flow】 一眼看全——Flow操作符大全
 * https://juejin.cn/post/6989536876096913439
 *
 * 关于kotlin中的Collections、Sequence、Channel和Flow (二)
 * https://juejin.cn/post/6979793981928374308
 *
 * 不做跟风党，LiveData，StateFlow，SharedFlow 使用场景对比 (优秀)
 * https://juejin.cn/post/7007602776502960165
 *
 * 用Kotlin Flow解决Android开发中的痛点问题
 * https://juejin.cn/post/7031726493906829319
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