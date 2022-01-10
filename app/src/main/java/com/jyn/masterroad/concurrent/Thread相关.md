[TOC]
# 线程
## 线程的创建
**大致可分为四种**
1. 继承`Thread`类
2. 实现`Runnable`接口
3. `FutureTask`类 + `Callable`接口
4. 借助`Executors`

#### **问: `new Thread()` 和 `new Object()` 有什么差别？**
- **`new Object()` 的过程**  
  1. 分配一块内存 M
  2. 在内存 M 上初始化该对象
  3. 将内存 M 的地址赋值给引用变量 obj  

- **`new Thread()` 的过程**
  1. 它为一个线程栈分配内存，该栈为每个线程方法调用保存一个栈帧
  2. 每一栈帧由一个局部变量数组、返回值、操作数堆栈和常量池组成
  3. 一些支持本机方法的 jvm 也会分配一个本机堆栈
  4. 每个线程获得一个程序计数器，告诉它当前处理器执行的指令是什么
  5. 系统创建一个与Java线程对应的本机线程
  6. 将与线程相关的描述符添加到JVM内部数据结构中
  7. 线程共享堆和方法区域

## sleep() 和 wait() 的区别
1. sleep是Thread类的方法,wait是Object类中定义的方法
2. Thread.sleep不会导致锁行为的改变，如果当前线程是拥有锁的，那么Thread.sleep不会让线程释放锁。
3. Thread.sleep和Object.wait都会暂停当前的线程，对于CPU资源来说，不管是哪种方式暂停的线程，都表示它暂时不再需要CPU的执行时间。
OS会将执行时间分配给其它线程。区别是，调用wait后，需要别的线程执行notify/notifyAll才能够重新获得CPU执行时间。

# java 中的 wait 和 notify
参考: [java中wait和notify](https://www.cnblogs.com/jerryshao2015/p/4419638.html)
  
JAVA的 synchronized()方法类似于操作系统概念中的互斥内存块。  

在JAVA 的 Object 类型中带有一个内存锁，当有线程获取该内存锁时，其它线程无法访问该内存，从而实现JAVA中简单的同步、互斥操作。  

synchronized就是针对内存区块申请内存锁，this关键字代表类的一个对象，所以其内存锁是针对相同对象的互斥操作。  

而static成员属于类专有，其内存空间为该类所有成员共有，这就导致synchronized()对static成员加锁，相当于对类加锁，
也就是在该类的所有成员间实现互斥，在同一时间只有一个线程可访问该类的实例。  

这就是JAVA中基本的线程互斥，而Object.wait(), Object.notify()其实就是线程的锁释放，和相互唤醒。

Obj.wait()，与Obj.notify()必须要与synchronized(Obj)一起使用，因为这两个方法都是针对已经获取了Obj锁的对象进行操作的。 

从语法角度来说就是Obj.wait()，Obj.notify()必须在synchronized(Obj){...}语句块内。  

wait()方法的主要作用是在线程在获取对象锁后，主动释放对象锁，同时本线程休眠。直到有其它线程调用对象的notify()唤醒该线程，才可能会继续获取对象锁，并继续执行。  

notify()方法就是对象锁的唤醒操作。但有一点需要注意的是notify()调用后，并不是马上就释放对象锁的，而是在相应的synchronized(){}语句块执行结束。  

自动释放锁后，JVM会在wait()对象锁的线程中随机选取一线程，赋予其对象锁，唤醒线程，继续执行。
这样就提供了在线程间同步、唤醒的操作。  

# 线程状态
> 可分为`操作系统通用线程状态` 和 `语言封装后的线程状态`
## 操作系统通用线程状态
对于操作系统而言，线程状态大致分为五种  

1. **初始** 
线程已被创建，但是还不被允许分配CPU执行。注意，这个被创建其实是属于编程语言层面的，实际在操作系统里，真正的线程还没被创建， 比如 Java 语言中的 new Thread()。

2. **可运行**  
线程可以分配CPU执行，这时，操作系统中线程已经被创建成功了

3. **运行**  
操作系统会为处在`可运行状态的线程`分配CPU时间片，被 CPU 临幸后，处在可运行状态的线程就会变为运行状态

4. **休眠**  
如果处在运行状态的线程调用某个`阻塞的API`或`等待某个事件条件可用`，那么线程就会转换到休眠状态。  
**注意**：此时线程会释放CPU使用权，休眠的线程永远没有机会获得CPU使用权，只有当等待事件出现后，线程会从休眠状态转换到可运行状态

5. **终止**  
线程执行完或者出现异常 (被interrupt那种不算，后续会说)就会进入终止状态，正式走到生命的尽头，没有起死回生的机会

```
初始 -> 可运行 <-> 运行 —> 中止
           ↘ 休眠 ↗
```

## Java封装后的线程状态
在Thread的源码中，可看到一个枚举类 State，可看到Java定义的6中线程状态。
1. **NEW**  
```
Thread thread = new Thread(() -> {})
```

2. **RUNNABLE**  
```
thread.start();
```

3. **BLOCKED**  
等待 synchronized 锁的时候，就会处在 BLOCKED 状态
```
new Thread(() -> { fun() }).start(); //占用 synchronized 锁
new Thread(() -> { fun() }).start(); //处于 BLOCKED

public synchronized void fun(){
    while(true) {
	    ...dosomething		
	}
}
```

4. **WAITING**  
```
Thread.join();
Object.wait();
LockSupport.park()
```

5. **TIMED_WAITING**  
```
Thread.sleep(1000);
```

6. **TERMINATED**  
```
执行结束
```

虽然状态变多了，但是Java语言的封装实际上让系统线程状态少了一种。  
```
初始 —> Runnable —> 终止
          ↑ ↓
          休眠
```
1. 将通用线程状态的`可运行状态`和`运行状态`合并为 `Runnable`。  
2. 将`休眠状态`细分为三种 (`BLOCKED`/`WAITING`/`TIMED_WAITING`); 反过来理解这句话，就是这三种状态在操作系统的眼中都是休眠状态，同样不会获得CPU使用权  
简单来说，我们现在只需要关心`Runnable`和`休眠状态`两种状态

### RUNNABLE 与 BLOCKED 状态转换
有且仅有（just only）一种情况会从 RUNNABLE 状态进入到 BLOCKED 状态，就是线程在等待 `synchronized 锁`；如果等待的线程获取到了 `synchronized 锁`，也就会从 BLOCKED 状态变为 RUNNABLE 状态了

> 上面提到，以操作系统通用状态来看，线程调用阻塞式 API，会变为休眠状态（释放CPU使用权），但在JVM层面，Java线程状态不会发生变化，也就是说Java线程的状态依旧会保持在 RUNNABLE 状态。JVM并不关心操作系统调度的状态。在JVM看来，等待CPU使用权（操作系统里是处在可执行状态）与等待I/O（操作系统是处在休眠状态），都是等待某个资源，所以都归入了RUNNABLE 状态。     
—— 摘自《Java并发编程实战》

### RUNNABLE 与 WAITING 状态转换
调用不带时间参数的等待API(`join()` `wait()`)，就会从RUNNABLE状态进入到WAITING状态；当被唤醒(`notify()`)就会从WAITING进入RUNNABLE状态

### RUNNABLE与 TIMED-WAITING 状态转换
调用带时间参数的等待API(`sleep()`)，自然就从 RUNNABLE 状态进入 TIMED-WAITING 状态；当被唤醒或超时时间到就会从TIMED_WAITING进入RUNNABLE状态

#### **问: 进入 BLOCKED 只有一种情况，就是等待 synchronized 监视器锁，那调用 JUC 中的 Lock.lock() 方法，如果某个线程等待这个锁，这个线程状态是什么呢？为什么？**
> WAITING

#### **问: 但既然都是阻塞，还要分成这 BLOCKED 和 WAITING 两种** 
> blocked 状态指的是进行系统调用，通过操作系统挂起线程后，线程的状态。  
而 waiting 状态则不需要进行系统调用，是一种 JVM 层面的线程阻塞后的状态。  
由于转换到 blocked 状态需要进行系统调用，所以到这个状态的转换操作比较重。

#### **问：线程的缓存何时刷新？**
[参考](https://juejin.cn/post/6844903749933072392)
> 1. 当从synchronized 代码域离开的时候；
> 2. 当线程结束时候；
> 3. 当调用synchronized方法；
> 4. 当第一次访问线程的某个属性。

# ThreadLocal 和 InheritableThreadLocal

## ThreadLocal 
一个现在只唯一，不能进行传递不可在线程间继承。子线程不能获取到父线程的ThreadLocal。  
当 ThreadLocal 调用 set或get 方法时，ThreadLocalMap 才会被真正创建，并用于存储数据

### ThreadLocal 内存泄漏
ThreadLocalMap 中的 Entry 的 key 使用的是 ThreadLocal 对象的弱引用，
在没有其他地方对ThreadLocal依赖，ThreadLocalMap中的ThreadLocal对象就会被回收掉，
但是对应的value不会被回收，这个时候Map中就可能存在key为null但是value不为null的项，这需要实际的时候使用完毕及时调用remove方法避免内存泄漏。

## InheritableThreadLocal
可以把父线程变量传递到子线程的ThreadLocal，不可逆向传递(子传父)
父线程创建子线程的时候，ThreadLocalMap中的构造函数会将父线程的inheritableThreadLocals中的变量复制一份到子线程的inheritableThreadLocals变量中。










