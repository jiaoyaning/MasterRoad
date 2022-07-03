package com.jyn.masterroad.jetpack.livedata

import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityLiveDataBinding
import com.jyn.masterroad.jetpack.livedata.test.LiveDataTestVM
import com.jyn.masterroad.jetpack.livedata.test.LiveDataTestVM.Companion.TAG
import com.jyn.masterroad.jetpack.livedata.test.MediatorLiveDataVM
import com.jyn.masterroad.jetpack.livedata.test.TransformationsVM

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
 * Jetpack 这么讲就懂了，LiveData原理、粘性事件掌握！
 * https://mp.weixin.qq.com/s/zW6X1CTnjdb3NX-d7nr6cw
 *
 * LiveData数据倒灌的解决方案之一
 * https://blog.csdn.net/ljcITworld/article/details/112849126
 *
 * 三方库源码笔记（8）-Retrofit 与 LiveData 的结合使用
 * https://mp.weixin.qq.com/s/jeJB2I0uV6LIRDwLqkMgEg
 *
 * android Livedata最详尽的使用场景分析，让你爱上Livedata
 * https://mp.weixin.qq.com/s/8y5PPOOBiX4jeKL1Ilp42Q
 *
 * 【带着问题学】关于LiveData你应该知道的知识点
 * https://juejin.cn/post/6892704779781275662
 *
 * DataBinding使用教程（三）：各个注解详解
 * https://blog.csdn.net/qiang_xi/article/details/75379321
 * DataBinding使用教程（四）：BaseObservable与双向绑定
 * https://blog.csdn.net/qiang_xi/article/details/77586836
 *
 * 关于LiveData可能引发的内存泄漏及优化
 * https://mp.weixin.qq.com/s/RsyOHc31ouBspyMoQG2ofw
 */
@Route(path = RoutePath.LiveData.path)
class LiveDataActivity : BaseActivity<ActivityLiveDataBinding>
    (R.layout.activity_live_data) {

    /**
     * LiveData最重要的一个特性是具有生命周期感知能力，当Activity或者Fragment处于活跃状态时，观察者才能观察到LiveData的变化。
     * 但是，如果我们把 Fragment 中的 LiveData 绑定的是 Activity 的生命周期时，会出现内存泄漏
     *
     * LiveData 本身是单进程的
     */

    /**
     * LiveData 数据倒灌的解决方案
     *  1.反射修改 ObserverWrapper 的 mLastVersion 使其与 mVersion相等
     *  2.SingleLiveEvent 添加一个标识
     */

    private val liveDataTestVM: LiveDataTestVM by viewModels()
    private val mediatorLiveDataVM: MediatorLiveDataVM by viewModels()
    private val transformationsVM: TransformationsVM by viewModels()

    override fun initData() {
        binding.model = liveDataTestVM
        binding.mediatorLiveData = mediatorLiveDataVM
        binding.transformationsVM = transformationsVM
        /**
         * 点击方法设置之，在xml中绑定OnClickListener接口
         */
        binding.onClick = View.OnClickListener {
            liveDataTestVM.subtract()
        }

        liveDataTestVM.num.observe(this) {
            LogUtils.tag(TAG).i("Observer -> num改变了$it")
            binding.tvNum.text = it.toString()
        }
    }
}