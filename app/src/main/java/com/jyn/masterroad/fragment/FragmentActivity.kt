package com.jyn.masterroad.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityFragmentBinding

/**
 * 详细聊聊Fragment的实现原理
 * https://mp.weixin.qq.com/s/0-dcgg3gNpfm4oV7j2w3vQ
 */
@Route(path = RoutePath.Fragment.path)
class FragmentActivity : BaseActivity<ActivityFragmentBinding>(R.layout.activity_fragment) {}