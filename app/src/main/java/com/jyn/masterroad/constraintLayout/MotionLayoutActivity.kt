package com.jyn.masterroad.constraintLayout

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityMotionLayoutBinding
import kotlinx.android.synthetic.main.activity_motion_layout.*

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
 */
@Route(path = RoutePath.MotionLayout.path)
class MotionLayoutActivity : BaseActivity<ActivityMotionLayoutBinding>
    (R.layout.activity_motion_layout) {

    override fun initView() {
        val constraintSet = binding.motionLayout.getConstraintSet(R.id.end)
        constraintSet.setRotation(R.id.ball, 180f)
        motionLayout.updateState(R.id.end, constraintSet)
    }
}