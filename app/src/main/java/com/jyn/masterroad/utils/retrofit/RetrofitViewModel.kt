package com.jyn.masterroad.utils.retrofit

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM
import com.jyn.masterroad.utils.retrofit.RetrofitService.Companion.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitViewModel(application: Application) : BaseVM(application) {
    companion object {
        const val TAG = "Retrofit"
    }

    fun callTest() {
        retrofitService
            .getArticleList(0)
            .enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    LogUtils.tag(TAG).i("onResponse response:$response")
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    LogUtils.tag(TAG).i("onFailure response:$t")
                }
            })
    }

    fun rxjavaTest() {
        retrofitService
            .getArticleList2(0)
            .subscribe({
                LogUtils.tag(TAG).i("onNext it:$it")
            }, {
                LogUtils.tag(TAG).i("onError it:$it")
            })
    }

    fun suspendTest() {
        viewModelScope.launch {
            val articleList3 = try {
                retrofitService.getArticleList3(0)
            } catch (e: Exception) {
                LogUtils.tag(TAG).i("articleList2 error :$e")
                null
            }
            LogUtils.tag(TAG).i("articleList2 success :$articleList3")
        }
    }

    fun liveDataTest() {
        retrofitService
            .getArticleList4(0)
            .observeForever {
                LogUtils.tag(TAG).i("observeForever it:$it")
            }
    }

    /**
     * 可以用来统一 try catch 网络请求
     */
    suspend inline fun <T> apiCall(crossinline call: suspend CoroutineScope.() -> Any): Any {
        return withContext(Dispatchers.IO) {
            val res: Any
            try {
                res = call()
            } catch (e: Throwable) {
                return@withContext e
            }
            return@withContext res
        }
    }
}