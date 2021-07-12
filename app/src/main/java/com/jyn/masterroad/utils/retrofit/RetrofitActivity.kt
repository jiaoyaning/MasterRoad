package com.jyn.masterroad.utils.retrofit

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityRetrofitBinding
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * 官网 https://square.github.io/retrofit/
 */
@Route(path = RoutePath.Retrofit.path)
class RetrofitActivity : BaseActivity<ActivityRetrofitBinding>
    (R.layout.activity_retrofit) {

}