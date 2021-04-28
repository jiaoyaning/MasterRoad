# 协程的创建
协程创建有三种方式

## 1. runBlocking
线程阻塞版，通常被用在单元测试和main函数中，平时的开发一般不会用到。
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
val coroutineScope = CoroutineScope(context) //使用CoroutineContext创建CoroutineScope对象，通过launch开启协程
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

  * **CoroutineDispatcher**
    > 协程调度器，决定协程所在的线程或线程池。
    可以指定协程运行于特定的一个线程、一个线程池，如果不指定任何线程（这样协程就会运行于当前线程）。
    `launch`函数定义如果不指定`CoroutineDispatcher`或者没有其他的`ContinuationInterceptor`，默认的协程调度器就是`Dispatchers.Default`。

    * **Dispatchers.Default**
      > 由JVM上的共享线程池支持。 默认情况下，使用的最大并行度为此调度程序的CPU内核数，但至少为两个。此调度程序经过了专门优化，适合在主线程之外执行占用大量 CPU 资源的工作。用例示例包括对列表排序和解析 JSON。 
      PS：这里的默认线程，其实和下面的IO线程共享同一个线程池。

    * **Dispatchers.IO**
      > 用于将阻塞的IO任务转移到共享线程池，此池中创建IO操作的线程，此调度程序经过了专门优化，适合在主线程之外执行磁盘或网络 I/O。示例包括使用 Room 组件、从文件中读取数据或向文件中写入数据，以及运行任何网络操作。 
      `kotlinx.coroutines.io.parallelism`的值限制，它默认为`64个线程`或`内核数`（以较大者为准）的限制。
    
    * **Dispatchers.Main**
      > 协程分派器，仅限于使用UI对象操作的Main线程。此调度程序只能用于与界面交互和执行快速工作。示例包括调用 suspend 函数，运行 Android 界面框架操作，以及更新 LiveData 对象。相当于`MainScope().launch{}`所创建的协程。

    * **Dispatchers.Unconfined**
      > 不局限于任何特定线程的协程调度程序，也就是哪个线程调用了该协程，就在该线程中运行。
      但是只是在第一个挂起点(比如`delay(1000)`)之前是这样的，挂起恢复后运行在哪个线程完全由所调用的挂起函数决定。
      非受限的调度器非常适用于执行不消耗 CPU 时间的任务，以及不更新局限于特定线程的任何共享数据（如UI）的协程。

## 2. CoroutineStart
  >协程启动的方式 ---> 四种
  * **CoroutineStart.DEFAULT**
    >根据其上下文立即安排协程执行
  * **CoroutineStart.LAZY**
  * **CoroutineStart.ATOMIC**
  * **CoroutineStart.UNDISPATCHED**


## 3. Suspend CoroutineScope

# 协程的两种启动方式
## launch
## async