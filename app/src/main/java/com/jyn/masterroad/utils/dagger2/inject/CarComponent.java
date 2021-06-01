package com.jyn.masterroad.utils.dagger2.inject;

import dagger.Component;

/**
 * Created by jiao on 2020/8/17.
 */
@Component
public interface CarComponent {
    void inject(Car car);
}
