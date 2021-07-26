package com.jyn.masterroad.base.jvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

/*
 * java深拷贝与浅拷贝
 * https://zhuanlan.zhihu.com/p/88262461
 *
 * 漫画：Object类很大，你忍一下
 * https://mp.weixin.qq.com/s/x2_xNxgn1HAdjwAOD2r5Hg
 */
public class ObjectTest {

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
        return super.equals(obj);
    }

    @NonNull
    @NotNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return super.toString();
    }

    /*
     * 当对象变成GC Roots不可达时，GC判断该对象是否覆盖了finalize()方法，若未覆盖，则直接将其回收；
     * 否则，若对象未执行过finalize()方法，将其放入F-Queue队列，由一低优先级线程执行该队列中对象的finalize()方法。
     * 执行finalize()方法完毕后，GC会再次判断该对象是否可达，若不可达，则进行回收;否则，对象“复活”。
     *
     * 子类可以override该方法，用于防止对象被回收，亦或是防止对象不被回收。
     * 要防止对象被回收，只需让该对象与GC ROOTS之间存在可达链即可。
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
