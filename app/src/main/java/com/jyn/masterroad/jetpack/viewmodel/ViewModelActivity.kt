package com.jyn.masterroad.jetpack.viewmodel

import androidx.activity.viewModels
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityViewModelBinding

/*
 * ViewModel源码详解
 * https://www.jianshu.com/p/4dc2b3fcac08
 *
 * 一道面试题：ViewModel为什么横竖屏切换时不销毁？（优秀）
 * https://juejin.cn/post/6951244272553181197
 *
 * 聊聊onSaveInstanceState和 onRetainNonConfigurationInstance 的区别
 * https://mp.weixin.qq.com/s/s3EUBhW9ovQG7DbZbvV7Bw
 *
 * ViewModel再问—字节真题
 * https://mp.weixin.qq.com/s/P74JqO8eKvtdBhdoCzYI8A
 *
 * ViewModel三问—阿里真题
 * https://mp.weixin.qq.com/s/5zIRbE69tWFZ3BIewusiLw
 *
 * 知识点 | ViewModel 四种集成方式
 * https://juejin.cn/post/6844904167853522957
 */
class ViewModelActivity : BaseActivity<ActivityViewModelBinding>(R.layout.activity_view_model) {
    val viewModelTest:ViewModelTest by viewModels()
}