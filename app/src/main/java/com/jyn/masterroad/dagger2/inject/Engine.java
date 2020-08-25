package com.jyn.masterroad.dagger2.inject;

import javax.inject.Inject;

/**
 * Created by jiao on 2020/8/17.
 */
public class Engine {
    @Inject
    Engine() {
    }

    public void run() {
        System.out.println("引擎转起来了~~~");
    }
}
