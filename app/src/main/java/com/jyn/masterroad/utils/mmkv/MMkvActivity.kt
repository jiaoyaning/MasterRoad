package com.jyn.masterroad.utils.mmkv

import com.jyn.masterroad.R
import com.jyn.common.Base.BaseActivity
import com.jyn.masterroad.databinding.ActivityMmkvBinding
import com.tencent.mmkv.MMKV

/**
 * Android 存储之MMKV
 * https://juejin.cn/post/7041591035272626183
 *
 * MMKV for Android 多进程设计与实现（官方 & 好文）
 * https://mp.weixin.qq.com/s/kTr1GVDCAhs5K7Hq3322FA
 *
 * Kotlin 委托的本质以及 MMKV 的应用
 * https://juejin.cn/post/7043843490366619685
 */
class MMkvActivity : BaseActivity<ActivityMmkvBinding>
(R.layout.activity_mmkv) {
    /**
     * mmap的优势
     *    1. MMAP对文件的读写操作只需要从磁盘到用户主存的一次数据拷贝过程，减少了数据拷贝次数，提高了文件操作效率
     *    2. MMAP使用逻辑内存对磁盘文件进行映射，操作内存就相当于操作文件，不需要开启线程，操作MMAP的速度和操作内存的速度一样
     *    3. MMAP提供一段可供随时写入的内存块，app只需要往里面写入数据，由操作系统在内存不足，进程退出等时候负责将内存回写到文件（也可以主动回写到文件中）
     *
     * mmap的劣势
     *    1. mmap必须映射整页的内存，可能会造成内存的浪费，所以mmap的适用场景是大文件的频繁读写
     *    2. 虽然写回文件的工作由系统负责，但是并不是实时的，是定期写回到磁盘的，中间如果发生内核崩溃、断电等，还是会丢失数据，不过可以通过msync将数据同步回磁盘
     */

    /**
     * MMKV如何保证多进程中的数据一致？
     *    1. 状态同步
     *       1.写指针的同步： 在每个进程内部缓存自己的写指针，然后在写入键值的同时，还要把最新的写指针位置也写到 mmap 内存中；这样每个进程只需要对比一下缓存的指针与 mmap 内存的写指针，如果不一样，就说明其他进程进行了写操作。
     *            事实上 MMKV 原本就在文件头部保存了有效内存的大小，这个数值刚好就是写指针的内存偏移量，我们可以重用这个数值来校对写指针。
     *       2.内存重整的感知：使用一个单调递增的序列号，每次发生内存重整，就将序列号递增。将这个序列号也放到 mmap 内存中，每个进程内部也缓存一份，只需要对比序列号是否一致，就能够知道其他进程是否触发了内存重整。
     *       3.内存增长的感知：MMKV 在内存增长之前，会先尝试通过内存重整来腾出空间，重整后还不够空间才申请新的内存。所以内存增长可以跟内存重整一样处理。至于新的内存大小，可以通过查询文件大小来获得，无需在 mmap 内存另外存放。
     *
     *    2. 文件锁
     *       加写锁时，如果当前已经持有读锁，那么先尝试加写锁，try_lock 失败说明其他进程持有了读锁，我们需要先将自己的读锁释放掉，再进行加写锁操作，以避免死锁的发生。
     *       解写锁时，假如之前曾经持有读锁，那么我们不能直接释放掉写锁，这样会导致读锁也解了。我们应该加一个读锁，将锁降级。
     *
     */

    /**
     * MMKV 可以匿名共享内存(Ashmem)
     *      用来传输大文件很适合
     */

    private val mmkv by lazy { MMKV.defaultMMKV() }

    override fun initData() {
        //todo https://blog.csdn.net/qq_36487432/article/details/82877889
        mmkv?.encode("test", "test")
    }
}