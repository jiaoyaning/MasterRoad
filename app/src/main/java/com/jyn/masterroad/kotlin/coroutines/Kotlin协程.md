[TOC]
# 什么是协程
> 概念
> * 挂起恢复
> * 程序自己处理挂起恢复
> * 程序自己处理挂起恢复来实现协程的协作运行

线程是操作系统运算调度的最小单元，需要一个堆栈，一个寄存器，一个优先权，虽说与进程相比，管理创建线程的开销都要小上很多，但是终究还是需要与系统内核打交道，不是语言和解释器本身能够决定的。  

线程占用CPU时间的时候才能运行，协程占用线程时间(线程在CPU上运行时的时间)的时候才能运行。  

倘若线程被操作系统挂起，限定于此线程调度的协程也自然无法运行，如果没有限定，协程调度器可以在原本运行所在的线程挂起时，将就绪的协程调度到另一个占着CPU时间的线程上运行。
# 协程的创建
协程创建有三种方式

## 1. runBlocking
线程阻塞版，通常被用在单元测试和main函数中。  
会直接将开启的协程调度到它的线程上，背后是一个event-loop再不断检测其上协程是否complete，阻塞所在线程，避免直接结束。 
```
runBlocking { //runBlocking是一个顶级函数
  ...
}
```
## 2. GlobalScope
不会阻塞线程，但是其的生命周期和应用一致，而且无法做到取消，所以也不推荐使用。
```
//方式二
GlobalScope.launch { //GlobalScope是一个单例对象，直接使用launch开启协程
  ...
}
```
## 3. CoroutineScope + CoroutineContext
通过`CoroutineContext`来创建一个`CoroutineScope`对象，通过`CoroutineScope.launch`或`CoroutineScope.async`可以开启协程，通过`CoroutineContext`也可以控制协程的生命周期，推荐使用。
```
//方式三
//使用CoroutineContext创建CoroutineScope对象，通过launch开启协程
val coroutineScope = CoroutineScope(context) 
coroutineScope.launch {
  ...
}
```

# 协程的参数
 协程一共有三个参数  
## 1. CoroutineContext 协程的上下文
  > `EmptyCoroutineContext`表示一个空的协程上下文。
  `CoroutineContext`是一个接口，主要用来调度协程本身。
  它的继承关系:
  `CoroutineContext` ---> `Element` ---> 
  `ContinuationInterceptor` ----> `CoroutineDispatcher`

**CoroutineDispatcher**
> 协程调度器，决定协程所在的线程或线程池。
可以指定协程运行于特定的一个线程、一个线程池，如果不指定任何线程（这样协程就会运行于当前线程）。
`launch`函数定义如果不指定`CoroutineDispatcher`或者没有其他的`ContinuationInterceptor`，默认的协程调度器就是`Dispatchers.Default`。

  * **Dispatchers.Default**
    > 由JVM上的共享线程池支持。 默认情况下，使用的最大并行度为此调度程序的CPU内核数，但至少为两个。此调度程序经过了专门优化，适合在主线程之外执行占用大量 CPU 资源的工作。用例示例包括对列表排序和解析 JSON。 
    **PS：这里的默认线程，其实和下面的IO线程共享同一个线程池。**

  * **Dispatchers.IO**
    > 用于将阻塞的IO任务转移到共享线程池，此池中创建IO操作的线程，此调度程序经过了专门优化，适合在主线程之外执行磁盘或网络 I/O。示例包括使用 Room 组件、从文件中读取数据或向文件中写入数据，以及运行任何网络操作。 
    `kotlinx.coroutines.io.parallelism`的值限制，它默认为`64个线程`或`内核数`（以较大者为准）的限制。
    
  * **Dispatchers.Main**
    > 协程分派器，仅限于使用UI对象操作的Main线程。此调度程序只能用于与界面交互和执行快速工作。示例包括调用 suspend 函数，运行 Android 界面框架操作，以及更新 LiveData 对象。相当于`MainScope().launch{}`所创建的协程。  

  * **Dispatchers.Unconfined**
    > 不局限于任何特定线程的协程调度程序，也就是哪个线程调用了该协程，就在该线程中运行。
    但是只是在第一个挂起点(比如`delay(1000)`)之前是这样的，挂起恢复后运行在哪个线程完全由所调用的挂起函数决定。
    非受限的调度器非常适用于执行不消耗 CPU 时间的任务，以及不更新局限于特定线程的任何共享数据（如UI）的协程。

## 2. CoroutineStart 四种启动模式
参考：[协程启动篇](https://mp.weixin.qq.com/s/nE2fW5ZBkbX2z_JeQmqkrA)  
  * **DEFAULT**
    > 立即执行协程体
    > 默认启动模式，我们可以称之为饿汉启动模式，因为协程创建后立即开始调度，虽然是立即调度，单不是立即执行，有可能在执行前被取消。
  * **LAZY**
    > 只有在需要的情况下运行
    > 懒汉启动模式，启动后并不会有任何调度行为，直到我们需要它执行的时候才会产生调度。也就是说只有我们主动的调用`Job`的`start`、`join`或者`await`等函数时才会开始调度，它有可能在调度前就被取消掉
  * **ATOMIC**、
    >立即执行协程体，但在开始运行之前无法取消
    > 原子启动模式，一样也是在协程创建后立即开始调度，但是它和`DEFAULT`模式有一点不一样，开始执行之前不能被取消，通过`ATOMIC`模式启动的协程执行到第一个挂起点之前是不响应`cancel` 取消操作的，`ATOMIC`一定要涉及到协程挂起后`cancel` 取消操作的时候才有意义。
  * **UNDISPATCHED**
    > 立即在当前线程执行协程体，直到第一个 suspend 调用
    > 协程在这种模式下会直接开始在当前线程下执行，直到运行到第一个挂起点。这听起来有点像 `ATOMIC`，不同之处在于`UNDISPATCHED`是不经过任何调度器就开始执行的。当然遇到挂起点之后的执行，将取决于挂起点本身的逻辑和协程上下文中的调度器。
    >
## 3.CoroutineScope 作用域
## 4.Suspend 挂起函数

# 协程的启动方式
## launch
## async

# 协程的线程切换
## withContext
withContext并不创建新的协程，只是用来改变协程的上下文，而仍然驻留在相同的协程中。

# 协程的作用域
[参考](https://juejin.cn/post/6953287252373930021)
协程作用域分为三种：
1. 顶级作用域 --> 没有父协程的协程所在的作用域称之为顶级作用域。 比如`MainScope`和`GlobalScope2` 
>
2. 协同作用域 --> 在协程中启动一个协程，新协程为所在协程的子协程。子协程所在的作用域默认为协同作用域。此时子协程抛出未捕获的异常时，会将异常传递给父协程处理，如果父协程被取消，则所有子协程同时也会被取消。
>
3. 主从作用域 官方称之为监督作用域。与协同作用域一致，区别在于该作用域下的协程取消操作的单向传播性，子协程的异常不会导致其它子协程取消。但是如果父协程被取消，则所有子协程同时也会被取消。

#PS
**问：为什么launch和async协程体内的日志输出是无序的？**
>因为协程采用的是并发设计模式。
但是如果某个协程满足以下几点，那它里面的子协程将会是同步执行的:
>1. 父协程的协程调度器是处于Dispatchers.Main情况下启动。
>2. 同时子协程在不修改协程调度器下的情况下启动。    
>
>如果其中的某一个子协程将他的协程调度器修改为非`Dispatchers.Main`，那么这个子协程将会与其他子协程并发执行

