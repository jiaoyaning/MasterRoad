package com.jyn.masterroad.dagger2.module;


import javax.inject.Inject;

/**
 * Created by jiao on 2020/8/17.
 */
public class Car2 {
    @Inject
    Engine2 engine;

    public Car2() {

    }

    public Engine2 getEngine() {
        return this.engine;
    }
}
