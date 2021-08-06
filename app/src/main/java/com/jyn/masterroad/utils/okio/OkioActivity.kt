package com.jyn.masterroad.utils.okio

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityOkioBinding

/*
 * 官网：https://square.github.io/okio/
 *
 * 用烂OkIO，隔壁产品看不懂了
 * https://mp.weixin.qq.com/s/5EdOHB_iOfJf2lYjOaIiJg
 */
@Route(path = RoutePath.Okio.path)
class OkioActivity : BaseActivity<ActivityOkioBinding>(R.layout.activity_okio) {
}