[TOC]  

![线程池提交任务与执行任务流程](https://mmbiz.qpic.cn/mmbiz_png/OyweysCSeLVMG2Z2WVksz8x3cqKQO6mnxRHH17cuyJ083gseYlic8snYyIQG523xgWN9x6Ll3Dz5HI8l0OE6FAA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
# Executors的几种线程创建方式
**1. `Executors.newCachedThreadPool()`**
>可缓存的线程池，如果线程池的容量超过了任务数，自动回收空闲线程，任务增加时可以自动添加新线程，线程池的容量不限制

**2. `Executors.newSingleThreadExecutor()`**
>单线程的线程池，线程异常结束，会创建一个新的线程，能确保任务按提交顺序执行

**3. `Executors.newFixedThreadPool(2)`**
>定长线程池，每当提交一个任务就创建一个线程，直到达到线程池的最大数量，这时线程数量不再变化，当线程发生错误结束时，线程池会补充一个新的线程  

**4. `Executors.newScheduledThreadPool(2)`**
>定长线程池，可定时及周期性任务执行（轮训）、延迟执行

---

# 线程池参数
**1. `corePoolSize`核心线程数**
>如果提交任务后线程还在运行，当线程数小于 `corePoolSize` 值时，无论线程池中的线程是否忙碌，都会创建线程，并把任务交给此新创建的线程进行处理，如果线程数少于等于 `corePoolSize` ，那么这些线程不会回收，除非将 `ThreadPoolExecutor.allowCoreThreadTimeOut()` 设置为 `true`，但一般不这么干，因为频繁地创建销毁线程会极大地增加系统调用的开销。

**2. `workQueue`阻塞队列**
> 如果线程数大于核心数`（corePoolSize）`且小于最大线程数`（maximumPoolSize）`，则会将任务先丢到阻塞队列里，然后线程自己去阻塞队列中拉取任务执行。
> 如果 `workQueue` 是无界队列，那么当线程数增加到 `corePoolSize` 后，永远不会再新增新的线程，也无法触发 `RejectedExecutionHandler` 拒绝策略，任务只会源源不断地填充到 `workQueue`，直到 `OOM`。
> 
> **有界队列常用的有两个**
> *  `LinkedBlockingQueue`: 链表构成的有界队列，按先进先出（FIFO）的顺序对元素进行排列，但注意在创建时需指定其大小，否则其大小默认为 `Integer.MAX_VALUE`，相当于无界队列了
> *  `ArrayBlockingQueue`: 数组实现的有界队列，按先进先出（FIFO）的顺序对元素进行排列。  
> 
>**无界队列**
> * `PriorityBlockingQueue`:优先级队列，任务插入的时候可以指定其权重以让这些任务优先执行，但这个队列很少用，原因很简单，线程池里的任务执行顺序一般是平等的，如果真有必须某些类型的任务需要优先执行，大不了再开个线程池好了，将不同的任务类型用不同的线程池隔离开来，也是合理利用线程池的一种实践。

**3. `maximumPoolSize`最大线程数**
>线程池中最大可创建的线程数，如果提交任务时队列满了且线程数未到达这个设定值，则会创建线程并执行此次提交的任务，如果提交任务时队列满了但线池数已经到达了这个值，此时说明已经超出了线池程的负载能力，就会执行拒绝策略，这也好理解，总不能让源源不断地任务进来把线程池给压垮了吧，我们首先要保证线程池能正常工作。

**4. `RejectedExecutionHandler`拒绝策略**
>* `AbortPolicy`：丢弃任务并抛出异常，这也是默认策略；
>* `CallerRunsPolicy`：用调用者所在的线程来执行任务，所以开头的问题「线程把任务丢给线程池后肯定就马上返回了?」我们可以回答了，如果用的是 `CallerRunsPolicy` 策略，提交任务的线程（比如主线程）提交任务后并不能保证马上就返回，当触发了这个 `reject` 策略不得不亲自来处理这个任务。
>* `DiscardOldestPolicy`：丢弃阻塞队列中靠最前的任务，并执行当前任务。
>* `DiscardPolicy`：直接丢弃任务，不抛出任何异常，这种策略只适用于不重要的任务。

**5. `keepAliveTime`线程存活时间**
>线程存活时间，如果在此时间内超出 `corePoolSize` 大小的线程处于 `idle` 状态，这些线程会被回收

---

#问题
#### 问：线程池```submit()``` 和 ```execute()``` 区别？
> submit:提交(可以提交多种行为Callable、Runnable、Future)
> execute:执行(只能执行Runnable)

>1. 接收的参数不一样;
    `submit()`入参可以为 `Callable<T>`，也可以为 `Runnable`，而且方法可以有返回值 `Future<T>`;  
    `execute()` 入参只有一个 `Runnable`
>2. `submit()`有返回值，而 `execute()`没有;  
    例如，有个 `validation`的 `task`，希望该`task`执行完后告诉我它的执行结果，是成功还是失败，然后继续下面的操作。
>3. `submit()`可以捕获`Exception`;
    例如，如果`task`里会抛出`checked`或者`unchecked exception`，而你又希望外面的调用者能够感知这些`exception`并做出及时的处理，那么就需要用到`submit`，通过对`Future.get()`进行抛出异常的捕获，然后对其进行处理。  

---
#### 问：`Callable<T>()` 和 `Runnable()` 区别？
> 相同点：
> 1. 都是接口
> 2. 都可以编写多线程程序
> 3. 都采用`Thread.start()`启动线程
> 
> 不同点：
> 1. `Runnable`没有返回值；`Callable`可以返回执行结果，是个泛型，和`Future、FutureTask`配合可以用来获取异步执行的结果
> 2. `Callable`接口的`call()`方法允许抛出异常；`Runnable`的`run()`方法异常只能在内部消化，不能往上继续抛  
> **PS**:`Callalble`接口支持返回执行结果，需要调用`FutureTask.get()`得到，此方法会阻塞主进程的继续往下执行，如果不调用不会阻塞。

---

#### 问： `shutdown()` 和 `shutdownNow()` 和 `awaitTermination()`区别？
>`shutdown()` 和 `shutdownNow()`作用于关闭线程池。  
>`awaitTermination()`作用为阻塞主线程 & 判断阻塞结束时线程池是否已被销毁

> **shutdown()**
>1. 将线程池的状态设置为 SHUTDOWN
>2. 不会立即停止线程池，会继续执行尚未结束的任务 & 任务队列中的任务，直到全部执行完毕  
>3. 不再接受新的任务，强行添加会报错(submit 和 execute 方式都一样) --RejectedExecutionException  

> **shutdownNow()**
>1. 将线程池的状态设置为 STOP
>2. 正在执行的任务会被尝试 interrupt()中断
>3. 没被执行的任务则被返回(不包含正在执行的任务)

> **awaitTermination()**
>1. awaitTermination()方法所在线程会陷入阻塞状态，阻塞时间就是所设置时间（阻塞时间可以设置超过线程池本身所需的执行时间），  
阻塞地点就是awaitTermination()方法。
>2. 线程池行为和shutdown()基本相同，只是阻塞时间内继续添加线程并不会抛出异常，过了阻塞期后可以继续添加
>3. 返回结果 = 阻塞结束时线程池是否已被销毁 (shutdown 和 shutdownNow方法可以销毁线程池，因此经常会和awaitTermination连用)
***