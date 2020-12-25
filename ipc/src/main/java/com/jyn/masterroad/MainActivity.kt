package com.jyn.masterroad

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.apkfuns.logutils.LogUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Messenger与AIDL的异同:
 * https://blog.csdn.net/jiwangkailai02/article/details/48098087
 *
 * https://github.com/leavesC/IPCSamples/blob/master/note/AndroidIPC%E6%9C%BA%E5%88%B6%EF%BC%883%EF%BC%89-AIDL.md
 */
val TAG = "AIDLTest"

fun Log(any: Any) {
    LogUtils.tag(TAG).i(any)
}

class MainActivity : AppCompatActivity() {
    private lateinit var aidlTestInterface: AidlTestInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtils.getLogConfig().configShowBorders(false)
        initService()
        initView()
    }

    /**
     * 先启动aidl_test进程，模仿server端
     */
    private fun initService() {
        val intent = Intent()
        intent.setClassName("com.jyn.masterroad", "com.jyn.masterroad.AidlTestService")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun initView() {
        send_aidl_serve.setOnClickListener {
            val testFun = aidlTestInterface.testFun(
                    AidlTestBean().also { it.x = 100;it.y = 200 },
                    AidlTestBean().also { it.x = 200;it.y = 200 })
            Log("MainActivity 获取结果" + testFun.name)
        }

        send_aidl_serve_in.setOnClickListener {
            aidlTestInterface.setInTest(AidlTestBean().also {
                it.y = 1
                it.x = 1
                it.name = "MainActivity setInTest"
            })
        }

        /**
         * 测试后发现，out Tag修饰的形参，set失败
         */
        send_aidl_serve_out.setOnClickListener {
            aidlTestInterface.setOutTest(AidlTestBean().also {
                it.y = 2
                it.x = 2
                it.name = "MainActivity setOutTest"
            })
        }

        send_aidl_serve_inout.setOnClickListener {
            aidlTestInterface.setInOutTest(AidlTestBean().also {
                it.y = 3
                it.x = 3
                it.name = "MainActivity setInOutTest"
            })
        }

        get_aidl_serve_bean.setOnClickListener {
            Log(aidlTestInterface.testBean.toString())
        }
    }

    private val serviceConnection: ServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                LogUtils.tag(TAG).i("MainActivity 失去链接:$name")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                aidlTestInterface = AidlTestInterface.Stub.asInterface(service)
                LogUtils.tag(TAG).i("MainActivity 链接成功:$name")
            }
        }
    }
}