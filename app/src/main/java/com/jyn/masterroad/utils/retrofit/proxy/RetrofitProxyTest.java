package com.jyn.masterroad.utils.retrofit.proxy;

import com.apkfuns.logutils.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RetrofitProxyTest {
    public static final String TAG = "Retrofit";

    public void proxyTest() {
        RetrofitProxyInterface retrofitProxyInterface = new RetrofitProxyInterface() {
            @Override
            public void testFun(String testArgs) {
                LogUtils.tag(TAG).i("这是一个RetrofitInterface的匿名内部实现类 testArgs：" + testArgs);
            }
        };

        Class<RetrofitProxyInterface> retrofitClass = RetrofitProxyInterface.class;
        RetrofitProxyInterface retrofitProxyInterfaceProxy = (RetrofitProxyInterface) Proxy.newProxyInstance(
                retrofitClass.getClassLoader(),
                new Class<?>[]{retrofitClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                        LogUtils.tag(TAG).i(" ---- 动态代理开始 ---- ");
                        Object invoke = method.invoke(retrofitProxyInterface, args); //指明代理对象
                        LogUtils.tag(TAG).i(" ---- 动态代理结束 ---- ");
                        return invoke;
                    }
                });

        retrofitProxyInterfaceProxy.testFun("哎，就是玩");
    }

}
