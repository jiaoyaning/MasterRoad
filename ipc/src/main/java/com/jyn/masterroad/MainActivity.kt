package com.jyn.masterroad

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import androidx.appcompat.app.AppCompatActivity
import com.apkfuns.logutils.LogUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Messenger与AIDL的异同:
 * https://blog.csdn.net/jiwangkailai02/article/details/48098087
 *
 * https://github.com/leavesC/IPCSamples/blob/master/note/AndroidIPC%E6%9C%BA%E5%88%B6%EF%BC%883%EF%BC%89-AIDL.md
 */
const val TAG = "AIDLTest"

class MainActivity : AppCompatActivity() {
    private lateinit var aidlTestInterface: AidlTestInterface
    private lateinit var messenger: Messenger
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

        val intent1 = Intent()
        intent1.setClassName("com.jyn.masterroad", "com.jyn.masterroad.AidlTestService")
        bindService(intent1, aidlServiceConnection, BIND_AUTO_CREATE)

        //需要在清单文件里配置 <action /> 才行
//        val intent2 = Intent("com.jyn.masterroad.AidlTestService.Action")
//        intent2.setPackage("com.jyn.masterroad")
//        bindService(intent2, serviceConnection, BIND_AUTO_CREATE)

        //通过 package 和 classname
//        val intent3 = Intent()
//        intent3.component = ComponentName("com.jyn.masterroad","com.jyn.masterroad.AidlTestService")
//        bindService(intent3, serviceConnection, BIND_AUTO_CREATE)

        val intent4 = Intent()
        intent4.setClassName("com.jyn.masterroad", "com.jyn.masterroad.MessengerTestService")
        bindService(intent4, messengerTestService, BIND_AUTO_CREATE)
    }


    private val testBean = AidlTestBean().also {
        it.y = 1
        it.x = 1
        it.name = "MainActivity 默认 testBean"
    }

    private fun initView() {
        send_aidl_serve.setOnClickListener {
            val testFun = aidlTestInterface.testFun(
                    AidlTestBean().also { it.x = 100;it.y = 200 },
                    AidlTestBean().also { it.x = 200;it.y = 200 })
            LogUtils.tag(TAG).i("MainActivity 获取结果" + testFun.name)
        }

        send_aidl_serve_in.setOnClickListener {
            LogUtils.tag(TAG).i("MainActivity setInTest 修改前：$testBean")
            aidlTestInterface.setInTest(testBean)
            LogUtils.tag(TAG).i("MainActivity setInTest 修改后：$testBean")
        }

        /**
         * 测试后发现，out Tag修饰的形参，set失败
         */
        send_aidl_serve_out.setOnClickListener {
            LogUtils.tag(TAG).i("MainActivity setOutTest 修改前：$testBean")
            aidlTestInterface.setOutTest(testBean)
            LogUtils.tag(TAG).i("MainActivity setOutTest 修改后：$testBean")
        }

        /**
         * inout bean类被两端同步修改
         */
        send_aidl_serve_inout.setOnClickListener {
            LogUtils.tag(TAG).i("MainActivity setInOutTest 修改前：$testBean")
            aidlTestInterface.setInOutTest(testBean)
            LogUtils.tag(TAG).i("MainActivity setInOutTest 修改后：$testBean")
        }

        /**
         * 完全不会等服务端处理数据，直接下一行代码
         *
         * oneway情况下：
         * 不能有返回值；
         * 不能有 out 类型的参数；
         * 只对不同进程的调用有用，本地进程无效，依然是同步调用；
         *
         * 适合用来通知服务端，不修改数据，也没有返回值
         */
        send_aidl_serve_oneway.setOnClickListener {
            LogUtils.tag(TAG).i("MainActivity oneWayTest 修改前：$testBean")
            aidlTestInterface.onewayTest(testBean)
            LogUtils.tag(TAG).i("MainActivity oneWayTest 修改后：$testBean")
            Thread.sleep(3000)
            LogUtils.tag(TAG).i("MainActivity oneWayTest 修改后 & sleep：$testBean")
        }

        send_messenger_serve_oneway.setOnClickListener {
            val message: Message = Message.obtain()


        }
    }

    private val aidlServiceConnection: ServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                LogUtils.tag(TAG).i("MainActivity AIDL 失去链接:${name?.className}")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                aidlTestInterface = AidlTestInterface.Stub.asInterface(service)
                LogUtils.tag(TAG).i("MainActivity AIDL 链接成功:${name?.className}")
            }
        }
    }

    private val messengerTestService: ServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                LogUtils.tag(TAG).i("MainActivity Messenger 失去链接:${name?.className}")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                LogUtils.tag(TAG).i("MainActivity Messenger 链接成功:${name?.className}")
                messenger = Messenger(service)
            }
        }
    }
}