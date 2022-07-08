package com.jyn.masterroad.jetpack.viewmodel

import androidx.activity.viewModels
import com.jyn.masterroad.R
import com.jyn.common.Base.BaseActivity
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
    /**
     * ViewModel 使用 该方法存储
     * [onRetainNonConfigurationInstance]
     */

    /**
     * [onSaveInstanceState] 和 [onRetainNonConfigurationInstance] 区别
     *   [onSaveInstanceState] 中保存的 Bundle 信息是存在内存中的，且因为是涉及到 Activity 的状态的保存，
     *      就需要交由 ActivityManager 进程去做一个管理，所以就需要 Binder 传输做一个跨进程的通信将 Bundle 的数据传递给 ActivityManager。
     *      因此 onSaveInstanceState 也涉及到了 Binder 传输，自然而然就受到 Binder 缓冲区大小的限制
     *
     *   [onRetainNonConfigurationInstance] 只在横竖屏切换的时候调用，
     *      onRetainNonConfigurationInstance 用的Object存储，存在 ActivityThread 里一个叫ActivityClientRecord的对象。
     *      然后ActivityThread在启动activity时，通过attach方法将ActivityClientRecord里的NonConfigurationInstances又传回给了activity。
     */
}