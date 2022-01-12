package com.jyn.masterroad.view.draw.view

/*
 * Android Bitmap 详解：关于 Bitamp 你所要知道的一切
 * https://mp.weixin.qq.com/s/pOpG6vtnu1IAQhXf6714EA
 *
 * 图形图像处理 - 我们所不知道的 Bitmap
 * https://www.jianshu.com/p/e430b95010c7
 *
 * 官网
 * https://developer.android.com/topic/performance/graphics/manage-memory.html
 */
class BitmapView {
    /**
     * 7.0 像素内存的分配是这样的：
     *      通过JNI调用java层创建一个数组
     *      然后创建native层Bitmap，把数组的地址传进去。
     *
     * Java层的Bitmap对象由垃圾回收器自动回收，而native层Bitmap印象中我们是不需要手动回收的
     *      在Bitmap构造方法中有一个 BitmapFinalizer类，重写finalize 方法，在java层Bitmap被回收的时候，
     *      BitmapFinalizer 对象也会被回收，finalize 方法肯定会被调用，在里面释放native层Bitmap对象。
     *
     * 8.0 之后的 bitmap 是存放在 native 堆的
     *      8.0 我们手动调用 recycle 方法，数据是会立即释放的，因为像素数据本身就是在 Native 层开辟的。
     *      但如果是在 8.0 以下，就算我们手动调用 recycle 方法，数据也是不会立即释放的，而是 DeleteWeakGlobalRef 交由 Java GC 来回收。
     *      建议大家翻译一下 recycle 方法注释。注意：以上的所说的释放数据仅代表释放像素数据，并未释放 Native 层的 Bitmap 对象。
     *
     *
     * 1. Android 2.3.3及更低版本，像素点数据(Pixel data)存储在原生内存(Native Memory)，
     *      但Bitmap对象则存储在Dalvik Heap.这可能导致存在原生内存(Native Memory)的像素点数据(Pixel data)不被正确释放，进而导致应用发生奔溃(Crash)。
     * 2. 在Android 3.0(API level 11) ~ Android 7.1(API level 25)中无论是Bitmap对象还是像素点数据(Pixel Data),都统一存储在Dalvik Heap。
     * 3. 然而从Android 8.0(API level 26) 开始，截至到2018年3月的版本，素点数据(Pixel Data)被存储到Native Heap。
     */
}