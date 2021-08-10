package com.jyn.masterroad.utils.okhttp.interceptor;

import com.apkfuns.logutils.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    public static final String TAG = "Okhttp";

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        //第一步，获得chain内的request，这个Request,就是我自己new 的 Request 对象
        Request request = chain.request();
        LogUtils.tag(TAG).i(request);

        //第二步，用chain执行request
        Response response = chain.proceed(request);
        LogUtils.tag(TAG).i(response);

        //第三步，返回response
        return response;
    }
}
