package com.jyn.masterroad.dagger2.module;

import dagger.Module;
import dagger.Provides;

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
