package com.jyn.masterroad.utils.arouter

import com.alibaba.android.arouter.facade.template.IProvider

interface HelloService: IProvider {
    fun sayHello(name:String):String
}