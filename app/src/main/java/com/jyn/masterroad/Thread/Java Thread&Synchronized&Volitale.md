# 一、线程的四种创建方式
## 1.继承Thread类
## 2.实现Runnable接口
## 3.FutureTask类 + Callable接口
## 4.借助Executors

# 二、线程池相关
## submit() 和 execute() 区别
1. 接收的参数不一样;
    submit()入参可以为 Callable<T>，也可以为 Runnable，而且方法可以有返回值 Future<T>;  
    execute() 入参只有一个 Runnable
2. submit()有返回值，而 execute()没有;  
    例如，有个 validation的 task，希望该task执行完后告诉我它的执行结果，是成功还是失败，然后继续下面的操作。
3. submit()可以进行Exception处理;
    例如，如果task里会抛出checked或者unchecked exception，而你又希望外面的调用者能够感知这些exception并做出及时的处理，那么就需要用到submit，通过对Future.get()进行抛出异常的捕获，然后对其进行处理。
## shutdown() 和 shutdownNow() 和 awaitTermination() 区别
> shutdown() 和 shutdownNow()作用于关闭线程池。  
> awaitTermination()作用为阻塞主线程 & 判断阻塞结束时线程池是否已被销毁    
### shutdown()
1. 将线程池的状态设置为 SHUTDOWN
2. 不会立即停止线程池，会继续执行尚未结束的任务 & 任务队列中的任务，直到全部执行完毕  
3. 不再接受新的任务，强行添加会报错(submit 和 execute 方式都一样) --RejectedExecutionException  

### shutdownNow()  
1. 将线程池的状态设置为 STOP
2. 正在执行的任务会被尝试 interrupt()中断
3. 没被执行的任务则被返回(不包含正在执行的任务)

### awaitTermination()
1. awaitTermination()方法所在线程会陷入阻塞状态，阻塞时间就是所设置时间（阻塞时间可以设置超过线程池本身所需的执行时间），  
阻塞地点就是awaitTermination()方法。
2. 线程池行为和shutdown()基本相同，只是阻塞时间内继续添加线程并不会抛出异常，过了阻塞期后可以继续添加
3. 返回结果 = 阻塞结束时线程池是否已被销毁 (shutdown 和 shutdownNow方法可以销毁线程池，因此经常会和awaitTermination连用)

## sleep() 和 wait() 的区别
1. sleep是Thread类的方法,wait是Object类中定义的方法
2. Thread.sleep不会导致锁行为的改变，如果当前线程是拥有锁的，那么Thread.sleep不会让线程释放锁。
3. Thread.sleep和Object.wait都会暂停当前的线程，对于CPU资源来说，不管是哪种方式暂停的线程，都表示它暂时不再需要CPU的执行时间。
OS会将执行时间分配给其它线程。区别是，调用wait后，需要别的线程执行notify/notifyAll才能够重新获得CPU执行时间。

# 二、java 中的 wait 和 notify
参考:  
java中wait和notify : [https://www.cnblogs.com/jerryshao2015/p/4419638.html](https://www.cnblogs.com/jerryshao2015/p/4419638.html)

>JAVA的进程同步是通过synchronized()来实现的，需要说明的是，JAVA的synchronized()方法类似于操作系统概念中的互斥内存块，
在JAVA中的Object类型中，都是带有一个内存锁的，在有线程获取该内存锁后，其它线程无法访问该内存，从而实现JAVA中简单的同步、互斥操作。
明白这个原理，就能理解为什么synchronized(this)与synchronized(static XXX)的区别了，
synchronized就是针对内存区块申请内存锁，this关键字代表类的一个对象，所以其内存锁是针对相同对象的互斥操作，
而static成员属于类专有，其内存空间为该类所有成员共有，这就导致synchronized()对static成员加锁，相当于对类加锁，
也就是在该类的所有成员间实现互斥，在同一时间只有一个线程可访问该类的实例。如果只是简单的想要实现在JAVA中的线程互斥，明白这些基本就已经够了。
但如果需要在线程间相互唤醒的话就需要借助Object.wait(), Object.notify()了。

>Obj.wait()，与Obj.notify()必须要与synchronized(Obj)一起使用，也就是wait()和notify()是针对已经获取了Obj锁的对象进行操作的。
从语法角度来说就是Obj.wait()，Obj.notify()必须在synchronized(Obj){...}语句块内。
从功能上来说wait()就是说线程在获取对象锁后，主动释放对象锁，同时本线程休眠。直到有其它线程调用对象的notify()唤醒该线程，才能继续获取对象锁，并继续执行。
相应的notify()就是对对象锁的唤醒操作。但有一点需要注意的是notify()调用后，并不是马上就释放对象锁的，
而是在相应的synchronized(){}语句块执行结束，自动释放锁后，JVM会在wait()对象锁的线程中随机选取一线程，赋予其对象锁，唤醒线程，继续执行。
这样就提供了在线程间同步、唤醒的操作。Thread.sleep()与Object.wait()二者都可以暂停当前线程，释放CPU控制权，
主要的区别在于Object.wait()在释放CPU同时，释放了对象锁的控制。











