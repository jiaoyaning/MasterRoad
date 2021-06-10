package com.jyn.masterroad.jetpack.livedata

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityLiveDataBinding
import com.jyn.masterroad.jetpack.livedata.LiveDataTestViewModel.Companion.TAG
import kotlinx.android.synthetic.main.activity_live_data.*

/*
 * 【带着问题学】关于LiveData你应该知道的知识点 TODO
 * https://juejin.im/post/6892704779781275662
 *
 * Android官方架构组件LiveData: 观察者模式领域二三事
 * https://juejin.cn/post/6844903748574117901
 *
 * 基于LiveData实现事件总线思路和方案
 * https://yutiantina.github.io/2019/09/20/%E5%9F%BA%E4%BA%8ELiveData%E5%AE%9E%E7%8E%B0%E4%BA%8B%E4%BB%B6%E6%80%BB%E7%BA%BF%E6%80%9D%E8%B7%AF%E5%92%8C%E6%96%B9%E6%A1%88/
 *
 * 用LiveData打造EventBus有很多问题？拷贝代码干！
 * https://mp.weixin.qq.com/s/P85hN-hk-XeSN5b2rHILNg
 *
 * Android 架构组件 LiveData 的实现
 * https://mp.weixin.qq.com/s/ir3DBkGt5mna3RDjTpRFOQ
 *
 * 面试官：请你仔细说说 Jetpack.md LiveData 的粘性事件？
 * https://www.jianshu.com/p/e5455038aba3
 *
 * Jetpack 这么讲就懂了，LiveData原理、粘性事件掌握！
 * https://mp.weixin.qq.com/s/zW6X1CTnjdb3NX-d7nr6cw
 *
 * LiveData取代EventBus？LiveData的通信原理和粘性事件刨析
 * https://mp.weixin.qq.com/s/SyHlop4gLDbrbp8S5urMAA
 *
 * LiveData数据倒灌的解决方案之一
 * https://blog.csdn.net/ljcITworld/article/details/112849126
 *
 * 三方库源码笔记（8）-Retrofit 与 LiveData 的结合使用
 * https://mp.weixin.qq.com/s/jeJB2I0uV6LIRDwLqkMgEg
 */
@Route(path = RoutePath.LiveData.path)
class LiveDataActivity : BaseActivity<ActivityLiveDataBinding>
    (R.layout.activity_live_data) {

    lateinit var viewModel: LiveDataTestViewModel

    override fun initData() {
        viewModel = createVM()
        binding.model = viewModel
        /**
         * 点击方法设置之，在xml中绑定OnClickListener接口
         */
        binding.onClick = View.OnClickListener {
            viewModel.subtract()
        }

        viewModel.num.observe(this, {
            LogUtils.tag(TAG).i("Observer -> num改变了$it")
            tv_num.text = it.toString()
        })

        viewModel.numString?.observe(this, {
            LogUtils.tag(TAG).i("Observer -> numString改变了$it")
        })

        viewModel.select.observe(this, {
            LogUtils.tag(TAG).i("Observer -> select:$it")
        })

        viewModel.postValueNum.observe(this, {
            LogUtils.tag(TAG).i("Observer -> postValueNum:$it")
        })
    }
}