# 一、线程的四种创建方式
## 1.继承Thread类
## 2.实现Runnable接口
## 3.FutureTask类 + Callable接口
## 4.借助Executors

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











