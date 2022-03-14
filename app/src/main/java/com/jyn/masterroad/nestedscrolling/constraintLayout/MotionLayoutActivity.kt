package com.jyn.masterroad.nestedscrolling.constraintLayout

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityMotionLayoutBinding

/*
 * 官方文档：https://developer.android.google.cn/training/constraint-layout/motionlayout?hl=zh_cn
 * 官方实例：https://developer.android.google.cn/training/constraint-layout/motionlayout/examples?hl=zh_cn
 *
 * https://juejin.cn/post/6854573206653812743
 * https://juejin.cn/post/6844903918598635534
 *
 * ConstraintLayout2.0一篇写不完之MotionEffect
 * https://mp.weixin.qq.com/s/Ne298l-oqCjLjqaHv0_nRA
 *
 * ConstraintLayout2.0一篇写不完之MotionLabel
 * https://mp.weixin.qq.com/s/3xoms9zu77UFCJmNOF9ZUw
 *
 * 利用MotionLayout实现RecyclerView折叠展开动画
 * https://juejin.cn/post/6908327119055650824
 *
 * Android转场动画的前世今生
 * https://juejin.cn/post/6976102174627463198
 *
 * 玩转 MotionLayout：实战效果展示
 * https://mp.weixin.qq.com/s/ClkHpaI_zlNw_kH5omVhXg
 *
 * MotionLayout动画从未如此简单！
 * https://mp.weixin.qq.com/s/3IAPd53rMOrLiIUDT520-w
 *
 * ConstraintLayout2.0一篇写不完之ViewTransition
 * https://mp.weixin.qq.com/s/UUhrMGsAbpgIQNbd-1IOBQ
 *
 * 真的有这么丝滑吗？MotionLayout的高级玩法我学会了！
 * https://mp.weixin.qq.com/s/u6xpJcHYTVix9HJgh0c9wQ
 */
@Route(path = RoutePath.MotionLayout.path)
class MotionLayoutActivity : BaseActivity<ActivityMotionLayoutBinding>
    (R.layout.activity_motion_layout) {

    override fun initView() {
        val constraintSet = binding.motionLayout.getConstraintSet(R.id.end)
        constraintSet.setRotation(R.id.ball, 180f)
        binding.motionLayout.updateState(R.id.end, constraintSet)
    }
}