# 协程的参数
 协程一共有三个参数  
* **CoroutineContext** 协程的上下文
  > `EmptyCoroutineContext`表示一个空的协程上下文。
  `CoroutineContext`是一个接口，主要用来调度协程本身。
  它的继承关系:
  `CoroutineContext` ---> `Element` ---> 
  `ContinuationInterceptor` ----> `CoroutineDispatcher`

  * **CoroutineDispatcher**
    > 协程调度器，决定协程所在的线程或线程池。
    它可以指定协程运行于特定的一个线程、一个线程池，如果不指定任何线程（这样协程就会运行于当前线程）。
    `launch`函数定义如果不指定`CoroutineDispatcher`或者没有其他的`ContinuationInterceptor`，默认的协程调度器就是`Dispatchers.Default`。

    1. Dispatchers.Default
     > 由JVM上的共享线程池支持。 默认情况下，使用的最大并行度为此调度程序的CPU内核数，但至少为两个。

    2. Dispatchers.IO
     > 
    
    3. Dispatchers.Main
     > 协程分派器，仅限于使用UI对象操作的Main线程。可以直接使用此分派器，相当于`MainScope().launch{}`所创建的协程。

    4. Dispatchers.Unconfined
     > 不局限于任何特定线程的协程调度程序。也就是哪个线程调用了该协程，就在该线程中运行。
     但是只是在第一个挂起点(比如`delay(1000)`)之前是这样的，挂起恢复后运行在哪个线程完全由所调用的挂起函数决定。
     非受限的调度器非常适用于执行不消耗 CPU 时间的任务，以及不更新局限于特定线程的任何共享数据（如UI）的协程。

* **CoroutineStart**          
  >协程的启动方式，它四种标准方式。协程调度器，决定协程所在的线程或线程池。
  它可以指定协程运行于特定的一个线程、一个线程池，如果不指定任何线程（这样协程就会运行于当前线程）。

* **suspend CoroutineScope** 协程的lambda函数体。  