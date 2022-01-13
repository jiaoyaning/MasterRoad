package com.jyn.masterroad.kotlin.coroutines

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.common.Base.BaseScopeActivity
import com.jyn.masterroad.R
import com.jyn.masterroad.databinding.ActivityKotlinCoroutinesBinding
import kotlinx.coroutines.*
import kotlin.coroutines.*

/*
 * 官方文档
 * https://developer.android.google.cn/kotlin/coroutines
 *
 * Kotlin 协程 系列教程
 * http://mp.weixin.qq.com/mp/homepage?__biz=MzIzMTYzOTYzNA==&hid=4&sn=eb02d1dc6f5d92096f214688c6f87196&scene=18#wechat_redirect
 *
 * 硬核万字解读——Kotlin协程原理解析 TODO
 * https://mp.weixin.qq.com/s/N9BiufCWTRuoh6J-QERlWQ
 *
 * 超长文，带你全面了解Kotlin的协程 TODO
 * https://mp.weixin.qq.com/s/bUK8UKg9ZXykhvbiASpyVg
 *
 * 使用协程，让网络世界更加美好
 * https://mp.weixin.qq.com/s/84fSUYQt3T_Fa5B5s5ihvA
 *
 * 史上最详Android版kotlin协程入门进阶实战  TODO
 * https://juejin.cn/post/6954250061207306253
 *
 * Android 补给站
 * https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzIzNTc5NDY4Nw==&action=getalbum&album_id=1528448460574752768&scene=173&from_msgid=2247484696&from_itemidx=1&count=3&nolastread=1#wechat_redirect
 *
 * 最全面的Kotlin协程: Coroutine/Channel/Flow 以及实际应用
 * https://juejin.cn/post/6844904037586829320
 *
 * https://lilei.pro/2019/11/17/kotlin-coroutines-01/
 * https://lilei.pro/2019/12/10/kotlin-coroutines-02/
 * https://lilei.pro/2019/12/13/kotlin-coroutines-03/
 * https://lilei.pro/2020/03/16/kotlin-coroutines-04/
 */
@ExperimentalCoroutinesApi
@Route(path = RoutePath.KotlinCoroutines.path)
class KotlinCoroutinesActivity : BaseScopeActivity<ActivityKotlinCoroutinesBinding>
(R.layout.activity_kotlin_coroutines) {
    companion object {
        const val TAG = "coroutine"
    }

    private val coroutinesCreate: CoroutinesCreate by lazy { createVM() }

    override fun initView() {
        binding.create = coroutinesCreate
        binding.channel = ChannelTest()
    }

    /*
     * kotlin协程硬核解读(5. Java异常本质&协程异常传播取消和异常处理机制)
     * https://blog.csdn.net/xmxkf/article/details/117200099
     */
    override fun initData() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            LogUtils.tag(TAG).i("coroutineExceptionHandler coroutineContext -> $coroutineContext")
            throwable.printStackTrace()
            LogUtils.tag(TAG).i("coroutineExceptionHandler throwable -> $throwable")
        }

        lifecycleScope.launch(coroutineExceptionHandler) {
            /**
             * 协程代码块中之所以能直接捕获在异步挂起函数中"抛出"的异常？
             *    是因为协程中通过调度器将子线程的异常对象切换到了协程所在线程，然后在协程代码块中throw抛出。
             *    如果我们在挂起函数的子线程中直接通过throw抛出异常，协程代码块中的try并不能捕获到，
             *    协程隐藏了线程切换的实现细节，让我们直接可以在协程代码块中捕获挂起函数中通过resume恢复的异常，从而将异步异常"变为"同步异常。
             */
//            try {
            val coroutine1 = coroutine1(11)
            LogUtils.tag(TAG).i("suspendCancellableCoroutine -> $coroutine1")

            val coroutine2 = coroutine2(10)
            LogUtils.tag(TAG).i("suspendCoroutine -> $coroutine2")
//            } catch (e: Exception) {
//                LogUtils.tag(TAG).i(e.message)
//            }
        }
    }


    /**
     * suspendCancellableCoroutine 和 suspendCoroutine 唯一的区别就是前者是可以取消的
     */

    private suspend fun coroutine1(x: Int): String =
            suspendCancellableCoroutine { continuation: CancellableContinuation<String> ->
                continuation.invokeOnCancellation {
                    LogUtils.tag(TAG).i("suspendCancellableCoroutine  invokeOnCancellation -> $it")
                }

                Thread {
                    Thread.sleep(1000)

                    if (x > 10) continuation.resume("大于10")
                    /*
                     * resumeWithException可以抛到协程线程，然后try catch住
                     * 如果用了 throw Exception() ，在挂起的线程中就会直接崩溃，主协程就无法try catch住了
                     */
                    else continuation.resumeWithException(Exception("Exception 小于10"))
                    continuation.cancel() // 只有suspendCancellableCoroutine才有的功能
                }.start()
            }

    private suspend fun coroutine2(x: Int): String =
            suspendCoroutine { continuation: Continuation<String> ->
                Thread {
                    Thread.sleep(1000)

                    if (x > 10) continuation.resume("大于10")
                    else continuation.resumeWithException(Exception("Exception 小于10"))
                }.start()
            }
}