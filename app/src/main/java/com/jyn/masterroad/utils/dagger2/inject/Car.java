package com.jyn.masterroad.utils.dagger2.inject;

import javax.inject.Inject;

/**
 * Created by jiao on 2020/8/17.
 */
public class Car {

    @Inject
    Engine engine;

    public Car() {
//        DaggerCarComponent.builder().build().inject(this);
    }

    public Engine getEngine() {
        return this.engine;
    }
}
