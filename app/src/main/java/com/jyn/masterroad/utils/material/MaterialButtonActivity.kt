package com.jyn.masterroad.utils.material

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityMaterialButtonBinding

/**
 * Android MaterialButton使用详解，告别shape、selector
 * https://mp.weixin.qq.com/s/jXKZMThRu0Y32w6hTb_YnQ
 */
@Route(path = RoutePath.MaterialButton.path)
class MaterialButtonActivity : BaseActivity<ActivityMaterialButtonBinding>
    (R.layout.activity_material_button)