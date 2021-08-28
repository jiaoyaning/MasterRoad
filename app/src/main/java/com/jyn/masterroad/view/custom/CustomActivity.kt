package com.jyn.masterroad.view.custom

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityCustomBinding

@Route(path = RoutePath.Custom.path)
class CustomActivity : BaseActivity<ActivityCustomBinding>
    (R.layout.activity_custom) {
}