package com.jyn.masterroad.utils.material

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityShapeableImageViewBinding

/**
 * Google 还发布了这个库？ 告别shape、各种 drawable...
 * https://mp.weixin.qq.com/s/z3FkJHTSkH_TFis_VOdkSw
 */
@Route(path = RoutePath.ShapeableImageView.path)
class ShapeableImageViewActivity : BaseActivity<ActivityShapeableImageViewBinding>
(R.layout.activity_shapeable_image_view) {

}