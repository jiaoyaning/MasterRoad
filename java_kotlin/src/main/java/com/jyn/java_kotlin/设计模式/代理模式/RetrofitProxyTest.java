package com.jyn.java_kotlin.设计模式.代理模式;

import com.jyn.java_kotlin.设计模式.代理模式.RetrofitProxy.RetrofitInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by jiaoyaning on 2021/8/3.
 */
public class RetrofitProxyTest {
    public static void main(String[] args) {
        RetrofitInterface retrofitInterface = new RetrofitInterface() {
            @Override
            public void testFun(String testArgument) {
                System.out.println("这是一个RetrofitInterface的匿名内部实现类 testArgument：" + testArgument);
            }
        };

        Class<RetrofitInterface> retrofitClass = RetrofitInterface.class;
        RetrofitInterface retrofitInterfaceProxy = (RetrofitInterface) Proxy.newProxyInstance(
                retrofitClass.getClassLoader(),
                new Class<?>[]{retrofitClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                        System.out.println(" ---- 动态代理开始 ---- ");
                        Object invoke = method.invoke(retrofitInterface, proxy, args);
                        System.out.println(" ---- 动态代理结束 ---- ");
                        return invoke;
                    }
                });

        retrofitInterfaceProxy.testFun("哎，就玩");
    }
}
