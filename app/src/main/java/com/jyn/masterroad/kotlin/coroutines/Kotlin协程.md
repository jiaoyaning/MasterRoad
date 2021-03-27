# 协程的参数
协程一共有三个参数  
* **CoroutineContext**  
  >协程的上下文，EmptyCoroutineContext，表示一个空的协程上下文。CoroutineContext是一个接口，主要用来调度协程本身，它的继承关系:
  `CoroutineContext ---> Element ---> ContinuationInterceptor ----> CoroutineDispatcher`
  * **CoroutineDispatcher**
    > 23

* **CoroutineStart**          
  >协程的启动方式，它四种标准方式。协程调度器，决定协程所在的线程或线程池。它可以指定协程运行于特定的一个线程、一个线程池，如果不指定任何线程（这样协程就会运行于当前线程）。

* **suspend CoroutineScope** 协程的lambda函数体。  