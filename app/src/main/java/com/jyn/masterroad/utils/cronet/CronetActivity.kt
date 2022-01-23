package com.jyn.masterroad.utils.cronet

import android.util.Log
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityCronetBinding
import org.chromium.net.CronetEngine
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/*
 * QUIC 协议框架
 * https://developer.android.com/guide/topics/connectivity/cronet?hl=zh-cn
 *
 * QUIC协议: Cronet构建与应用
 * https://mp.weixin.qq.com/s/u-GIkhfhJHjSl41GNkgQQw
 * https://github.com/akshetpandey/react-native-cronet
 *
 * 科普：QUIC协议原理分析
 * https://zhuanlan.zhihu.com/p/32553477
 *
 * QUIC专题 | 谈谈QUIC协议原理 (腾讯深度好文)
 * https://mp.weixin.qq.com/s/pa5lLcNtsEYRBHrUAJYcJA
 */
class CronetActivity : BaseActivity<ActivityCronetBinding>
(R.layout.activity_cronet) {
    companion object {
        const val TAG = "MyUrlRequestCallback"
    }

    val executor: Executor = Executors.newSingleThreadExecutor()
    val myBuilder = CronetEngine.Builder(this)
    val cronetEngine: CronetEngine = myBuilder.build()

    fun test() {
        val requestBuilder = cronetEngine.newUrlRequestBuilder(
                "https://www.example.com",
                MyUrlRequestCallback(),
                executor
        )

        val request: UrlRequest = requestBuilder.build()

        request.start()
    }

    class MyUrlRequestCallback : UrlRequest.Callback() {
        //重定向
        override fun onRedirectReceived(request: UrlRequest?, info: UrlResponseInfo?, newLocationUrl: String?) {
            Log.i(TAG, "onRedirectReceived method called.")
            // You should call the request.followRedirect() method to continue
            // processing the request.
            request?.followRedirect()
        }


        override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
            Log.i(TAG, "onResponseStarted method called.")
            // You should call the request.read() method before the request can be
            // further processed. The following instruction provides a ByteBuffer object
            // with a capacity of 102400 bytes to the read() method.
            request?.read(ByteBuffer.allocateDirect(102400))
        }

        override fun onReadCompleted(request: UrlRequest?, info: UrlResponseInfo?, byteBuffer: ByteBuffer?) {
            Log.i(TAG, "onReadCompleted method called.")
            // You should keep reading the request until there's no more data.
            request?.read(ByteBuffer.allocateDirect(102400))
        }

        override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
            Log.i(TAG, "onSucceeded method called.")
        }

        override fun onFailed(request: UrlRequest?, info: UrlResponseInfo?, error: CronetException?) {
            Log.i(TAG, "onFailed method called.")
        }
    }
}