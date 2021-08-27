package com.jyn.masterroad.view.viewstub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jyn.masterroad.R

/**
 * 高频面试题 | 关于 ViewStub 的秘密
 * https://mp.weixin.qq.com/s/JOkbcuiaolMlyg2UNuhsqg
 *
 * ViewStub 你真的了解吗
 * https://mp.weixin.qq.com/s/oIyhDp8hBkGTb6WOasJd3Q
 */
class ViewStubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stub)
    }
}