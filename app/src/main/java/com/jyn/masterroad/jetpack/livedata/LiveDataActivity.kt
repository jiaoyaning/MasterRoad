package com.jyn.masterroad.jetpack.livedata

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityLiveDataBinding
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
 */
@Route(path = RoutePath.LiveData.path)
class LiveDataActivity : BaseActivity<ActivityLiveDataBinding>(R.layout.activity_live_data) {

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

        viewModel.num.observe(this, Observer {
            LogUtils.tag("main").i("Observer -> num改变了$it")
            tv_num.text = it.toString()
        })

        viewModel.numString?.observe(this, Observer {
            LogUtils.tag("main").i("Observer -> numString改变了$it")
        })
    }
}