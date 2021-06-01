package com.jyn.masterroad.utils.dagger2.module;

import com.jyn.masterroad.utils.dagger2.inject.Car;

/**
 * Created by jiao on 2020/8/17.
 */
//@Component(modules = {MarkCarModule.class})
public interface CarComponent2 {
    void inject(Car car);
}
