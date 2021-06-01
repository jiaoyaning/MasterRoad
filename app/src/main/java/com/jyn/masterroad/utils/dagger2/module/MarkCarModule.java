package com.jyn.masterroad.utils.dagger2.module;

/**
 * Created by jiao on 2020/8/17.
 */
//@Module
class MarkCarModule {
    public MarkCarModule() {
    }

//    @Provides
    Engine2 provideEngine() {
        return new Engine2("gear");
    }
}
