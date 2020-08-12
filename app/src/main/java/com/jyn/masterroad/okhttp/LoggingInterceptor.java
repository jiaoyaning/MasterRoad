package com.jyn.masterroad.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiao on 2020/8/11.
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //第一步，获得chain内的request，这个Request,就是我自己new 的 Request 对象
        Request request = chain.request();
        long t1 = System.nanoTime(); // 获取发送请求是时间

        //第二步，用chain执行request
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        //第三步，返回response
        return response;
    }
}
