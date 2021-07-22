package com.jyn.masterroad.jetpack.viewmodel

import androidx.activity.viewModels
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityViewModelBinding

/*
 * ViewModel 从入门到精通
 * https://www.jianshu.com/p/606ae661d025
 *
 * ViewModel源码详解
 * https://www.jianshu.com/p/4dc2b3fcac08
 *
 * https://mp.weixin.qq.com/s/dWdcHGTqMlMIuLHeCojhoQ
 *
 * 一道面试题：ViewModel为什么横竖屏切换时不销毁？（优秀）
 * https://juejin.cn/post/6951244272553181197
 *
 * 聊聊onSaveInstanceState和onRetainNonConfigurationInstance的区别
 * https://mp.weixin.qq.com/s/s3EUBhW9ovQG7DbZbvV7Bw
 */
class ViewModelActivity : BaseActivity<ActivityViewModelBinding>(R.layout.activity_view_model) {
    val viewModelTest:ViewModelTest by viewModels()
}