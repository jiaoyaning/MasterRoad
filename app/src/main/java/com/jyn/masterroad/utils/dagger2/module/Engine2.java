package com.jyn.masterroad.utils.dagger2.module;

/**
 * Created by jiao on 2020/8/17.
 */
public class Engine2 {
    private String gear;

    Engine2(String gear) {
        this.gear = gear;
    }

    public void run() {
        System.out.println("引擎转起来了~~~ gear:" + gear);
    }
}
