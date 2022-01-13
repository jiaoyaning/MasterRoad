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
                        LogUtils.tag(TAG).i("callTest onResponse response:$response")
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        LogUtils.tag(TAG).i("callTest onFailure response:$t")
                    }
                })
    }

    fun rxjavaTest() {
        retrofitService
                .getArticleList2(0)
                .subscribe({
                    LogUtils.tag(TAG).i("rxjavaTest onNext it:$it")
                }, {
                    LogUtils.tag(TAG).i("rxjavaTest onError it:$it")
                })
    }

    /**
     *
     * 怎么判断是否是suspend的？
     *      HttpServiceMethod.parseAnnotations
     *      RequestFactory.parseParameter
     *      Utils.getRawType(parameterType) == Continuation.class
     *
     * 怎么处理的协程请求？
     *      HttpServiceMethod.parseAnnotations
     *      获取 suspend 函数返回类型，判断是否是 Response<T> 类型 continuationWantsResponse = true 或者 false
     *      根据 continuationWantsResponse 判断返回 SuspendForResponse 还是 SuspendForBody 对象
     *      在 SuspendForResponse / SuspendForBody 的 adapt 方法中调用 KotlinExtensions.awaitResponse / awaitNullable 方法获取 response 并唤起协程返回数据
     */
    fun suspendTest() {
        viewModelScope.launch {
            val articleList3 = try {
                retrofitService.getArticleList3(0)
            } catch (e: Exception) {
                LogUtils.tag(TAG).i("suspendTest error :$e")
                null
            }
            LogUtils.tag(TAG).i("suspendTest success :$articleList3")
        }
    }

    //同步请求
    suspend fun suspendResponseTest() {
        val body = retrofitService.getArticleList5(0).body()
        LogUtils.tag(TAG).i("suspendResponseTest success :$body")
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