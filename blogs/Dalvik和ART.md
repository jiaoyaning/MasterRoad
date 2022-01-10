[TOC]

# Dalvik

DVM 基于寄存器，JVM 基于栈
> 寄存器是 CPU 上面的一块存储空间，栈是内存上面的一段连续的存储空间，
> 所以 CPU 直接访问自己上面的一块空间的数据的效率肯定要大于访问内存上面的数据。
> 基于栈架构的程序在运行时虚拟机需要频繁的从栈上读取或写入数据，这个过程需要更多的指令分派与内存访问次数，会耗费不少 CPU 时间，
> 对于像手机设备资源有限的设备来说，这是相当大的一笔开销。DVM 基于寄存器架构。数据的访问通过寄存器间直接传递，这样的访问方式比基于栈方式要快很多。

# ART

ART 即 Android Runtime，是在 Dalvik 的基础上做了一些优化。
> 在 Dalvik 下，应用每次运行的时候，字节码都需要通过即时编译器（ JIT, just in time ）转换为机器码，
> 这会拖慢应用的运行效率，而在 ART 环境中，应用在第一次安装的时候，字节码就会预先编译成机器码，使其成为真正的本地应用。
> 这个过程叫做预编译（ AOT, Ahead-Of-Time ）。这样的话，应用的启动(首次)和执行都会变得更加快速。

# JIT 和 AOT

# dex＆odex＆oat

## vdex

在讲odex之前，需要先讲vdex（Android O开始加入的）  
package直接转化的 可执行二进制码 文件：   
1.第一次开机就会生成在/system/app/<packagename>/oat/下；   
2.在系统运行过程中，虚拟机将其 从“/system/app”下 copy到“/data/davilk-cache/”下
> 为何要搞出个vdex文件
> 目的不是为了提升性能，而是为了避免不必要的验证Dex 文件合法性的过程，例如首次安装时进行dex2oat时会校验Dex 文件各个section的合法性，
> 这时候使用的compiler filter 为了照顾安装速度等方面，并没有采用全量编译，当app盘启动后，运行一段时间后，收集了足够多的jit 热点方法信息，
> Android会在后台重新进行dex2oat, 将热点方法编译成机器代码，这时候就不用再重复做验证Dex文件的过程了，

## odex( Optimized dex )，即优化的 dex，主要是为了提高 DVM 的运行速度。

1. 如果当前运行在 Dalvik 虚拟机下，Dalvik 会对 classes.dex 进行一次“翻译”，“翻译”的过程也就是守护进程 installd 的函数 dexopt 来对 dex
   字节码进行优化，实际上也就是由 dex 文件生成 odex 文件， 最终 odex 文件被保存在手机的 VM 缓存目录 data/dalvik-cache 下（注意！这里所生成的 odex
   文件依旧是以 dex 为后缀名，格式如：system@priv-app@Settings@Settings.apk@classes.dex）
2. 如果当前运行于 ART 模式下， ART 同样会在首次进入系统的时候调用 /system/bin/dexopt （此处应该是 dex2oat 工具吧）工具来将 dex
   字节码翻译成本地机器码，保存在 data/dalvik-cache 下。 那么这里需要注意的是，无论是对 dex 字节码进行优化，还是将 dex
   字节码翻译成本地机器码，最终得到的结果都是保存在相同名称的一个 odex 文件里面的，但是前者对应的是一个 .dex 文件（表示这是一个优化过的 dex），后者对应的是一个 .oat 文件。

优化：

1. 减少了启动时间（省去了系统第一次启动应用时从apk文件中读取dex文件，并对dex文件做优化的过程。）
   和对RAM的占用（apk文件中的dex如果不删除，同一个应用就会存在两个dex文件：apk中和data/dalvik-cache目录下）。

2. 防止第三方用户反编译系统的软件（odex文件是跟随系统环境变化的，改变环境会无法运行；而apk文件中又不包含dex文件，无法独立运行）。

odex 进行优化 生成的 可执行二进制码 文件，主要是apk 启动的常用函数相关地址的记录，方便寻址相关； 



[官方](https://source.android.com/devices/tech/dalvik?hl=zh-cn)
[apk dex vdex odex art 区别](https://www.jianshu.com/p/f48eac038384)  
[快速理清 .dex、.odex、ART、AOT、OAT 逻辑关系](https://mp.weixin.qq.com/s/lPFhnu2cXeDw19l0XE2GDQ)  
[Dalvik、ART、DEX、ODEX、JIT、AOT、OAT 傻傻分不清楚？](https://mp.weixin.qq.com/s/e-FsGkXFHZ-Tp51mTj2jsQ)  
[内存管理概览](https://developer.android.com/topic/performance/memory-overview?hl=zh-cn)
[进程间的内存分配](https://developer.android.com/topic/performance/memory-management?hl=zh-cn)
