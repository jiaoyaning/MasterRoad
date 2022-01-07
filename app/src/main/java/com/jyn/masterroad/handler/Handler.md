[TOC]
# Handle
####**问：`handle`的作用？**
1. 在Android系统中需要一个切换线程的工具。
2. 同时需要在某些情况下进行delay操作。

****

####**问：`handle`的三种使用方式？**
1. 给`Message`里添加一个`callback`参数。
   ```java
    Handler handler = new Handler();
    Message.obtain(handler, new Runnable() {
        @Override
        public void run() {

        }
    });
   ```
2. 初始化`Handler`的时候带上一个`Callback`参数。
   ```java
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            return false; //返回true的时候，不会执行Handler的handleMessage()方法
        }
    });
   ```
3. 重写`Handler`的`handleMessage()`方法。
   ```java
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };
   ```

**总结** :`Handler`其实就是一个发送器/接收器，用来将`Message`发送到`MessageQueue`中，当消息被处理之后会通过`Message.target`回调至`Handler`中进行处理。
1. `Handler`对`Message`设置`delay`，同时将自己`(Handler.this)`设置为`Message`的`target`，并将其添加至`MessageQueue`中。
2. `Looper`不断循环从`MessageQueue`中取出`Message`，并根据前面提到的优先级决定在什么情况下回调`Message.target`对应的`Handler`。

****

####**问：三种`Message`的优先级**  
>`Message.callback` > `Handler.Callback.handleMessage` > `Handler.handleMessage`
总结：消息`callback` > 形参`callback` > `Handler`方法

****

####**问：`Handler.Callback.handleMessage`的返回值有什么影响**
>返回`true`的时候，不再执行`handle`本身的`handleMessage`分发方法。

```java
public void dispatchMessage(@NonNull Message msg) {
    // 如果msg有callback则直接处理msg的callback
    if (msg.callback != null) {
        handleCallback(msg);
    } else {
        // 如果已通过构造函数设置mCallback则只处理该callback
        if (mCallback != null) {
            // mCallback处理成功直接返回
            if (mCallback.handleMessage(msg)) {
                return;
            }
        }
        // 最后才通过Handler自己的回调处理Message
        handleMessage(msg);
    }
}
```

***

####**问：`Handler`内存泄漏问题?**
[参考](https://juejin.cn/post/6909362503898595342)
>答："内部类持有了外部类的引用，也就是Hanlder持有了Activity的引用，从而导致无法被回收呗。"
 其实这样回答是错误的，或者说没回答到点子上。
>
> 其实必需正在运行线程的内部类，比如`AsyncTask`这种，内部线程作为`GC Root`，持有了这个类的引用。  
> 
> 我们必须找到那个最终的引用者，不会被回收的引用者，其实就是主线程，这条完整引用链应该是这样：
> > **`主线程(ActivityThread)` —> `threadlocal` —> `Looper` —> `MessageQueue` —> `Message` —> `Handler` —>`Activity`**


##### **延伸问题1：内部类为什么会持有外部类的引用?**
> 因为内部类虽然和外部类写在同一个文件中，但是编译后还是会生成不同的class文件，其中内部类的构造函数中会传入外部类的实例，然后就可以通过this$0访问外部类的成员。
>``` java
> //原代码
>class InnerClassOutClass{
>    class InnerUser {
>       private int age = 20;
>    }
>}
>
>//class代码
>class InnerClassOutClass$InnerUser {
>    private int age;
>    InnerClassOutClass$InnerUser(InnerClassOutClass var1) {
>        this.this$0 = var1;
>        this.age = 20;
>     }
>}
>```

##### **延伸问题2：`kotlin`中的内部类与`Java`有什么不一样吗?**
>1. `在Kotlin中`，匿名内部类如果没有使用到外部类的对象引用时候，是不会持有外部类的对象引用的，此时的匿名内部类其实就是个`静态匿名内部类`，也就不会发生内存泄漏。
>2. `在Kotlin中`，匿名内部类如果使用了对外部类的引用，这时候就会持有外部类的引用了，就会需要考虑内存泄漏的问题。
>
>同样`kotlin`中对于内部类也是和`Java`有区别的。
>1. `Kotlin`中所有的内部类都是默认静态的，也就都是静态内部类。
>2. 如果需要调用外部的对象方法，就需要用`inner`修饰，改成和`Java`一样的内部类，并且会持有外部类的引用，需要考虑内存泄漏问题。


##### **问：为什么Handler不会引起ANR？**
> ANR的管理是在AMS中进行一个Handler的延时事件，当到达时间后还没有拆出掉这个handler事件将会爆出ANR异常。而Hander整个过程并没有涉及到这些流程？

---



# 延伸: HandlerThread

---

# Message
1. 可以携带`Runnable`回调，被优先处理并拦截`Handler`的处理方法
2. `target`属性持有了`Handler`引用，可在`message`分发时回调到特定的`Handler`

 **有三种case可以直接想链表头添加一个Message**
 1. 当前消息队列中没有消息
 2. 新的Message延迟时间为0
 3. 新的Message延迟时间小于当前第一个Message的延迟时间
 ```java
 boolean enqueueMessage(Message msg, long when) {
    ...
    if (p == null || when == 0 || when < p.when) {
        msg.next = p;
        mMessages = msg;
        needWake = mBlocked;
    }else {

    }
    ...
 }
 ```

 ##### **问：锁屏后的延时`Message`会按时送到么？**

 ## MessageQueue  

 **Handler的Message种类分为3种：**
>1. 普通消息
>2. 屏障消息
>3. 异步消息
 
****
 
**`next()`方法的工作**
>1. 处理可以立即执行的消息
>2. 如果有同步屏障则优先处理异步消息
>3. 如果没有可以立即处理的消息就将队列阻塞起来
>4. 阻塞的时候根据情况决定是否回调IdleHandler

****

**`quit(boolean safe)`方法**
由`Looper`调用，`Looper`调用时并不是立刻真正退出，而是将类成员`mQuitting`设置为`true`。
在`Looper`通过`MessageQueue.next()`方法对消息队列进行遍历的时候，如果遇到`mQuitting == true`再去执行真正的退出。
这样的好处就是，一切行为都由`Looper`去触发，`MessageQueue`只负责维护`Message`以及记录状态。

>**问：`safe`与`!safe`的区别?**
>>1. `safe`的情况下会触发`removeAllFutureMessagesLocked()`方法，只移除现在还不能处理`(when > now)`的`Message`
>>2. `!safe`的情况下触发`removeAllMessagesLocked()`，将消息队列全部清空

****

####**问：为什么要用`for(;;)`而不是`while(1)`?**
>1. `for(;;)`死循环里的两个`;;`代表两个空语句，编译器一般会优化掉它们，直接进入循环体。
>2. `while(1)`死循环里的1被看成表达式，每循环一次都要判断常量1是不是等于零。   
> 
>显然`for(;;)`指令少，不占用寄存器，而且没有判断、跳转，比`while(1)`好。

****

####**问：`MessageQueue`为什么要用链表结构?**
>1. `Message`在系统中数量很多，如果用`array`实现的话会占用大量连续内存空间
>2. `Message`虽然数量很多但是处理的也很快，而且数量不定，也就是说如果用数组实现的话为了避免空间浪费，则需要对数组不停的进行扩容和减容，非常影响效率，而且会造成内存抖动

# Looper
#### **问：Looper.loop()启动了一个死循环，为什么没有阻塞主线程？**
>死循环不会阻塞主线程的原因是，死循环本身就是这个线程的主要任务！ 
再来想一想`Looper`是干嘛的？`Looper`是从`MessageQueue`里面取出消息并回调`Handler`处理的。
>当队列没有消息的时候证明主线程也不再需要刷新任何UI，也不需要处理任何数据，所以为了不让`Looper`空转导致资源浪费才有了`MessageQueue`的`阻塞机制 epoll`。  
>`epoll`是一个异步回调监听事件变化状态的系统调用，当没有事件的时候将会进入到进程挂起。


#### **延伸问题：整个线程处于死循环中，那么外部的事件是如何进入到`MessageQueue`中的呢？**  
> 其实这个问题也很容易理解，我们知道`Activity`全是由`AMS`统一维护的，`Activity`在启动的时候就和`AMS`建立了联系，如果有外部事件（触摸等）需要被处理的话，会由`AMS`通过`App`中的其他线程将`Message`抛到主线程的`MessageQueue`中。

#### **Looper怎么判断是否是活跃的？**
> Looper.myQueue().isPolling()
> MessageQueue.isPolling()

# 消息屏障与异步消息

# epoll原理 
[源码茶舍之没有epoll就没有Handler](https://juejin.cn/post/6896495861954510861)
epoll_wait这里也是整个Android消息机制阻塞的真正位置，阻塞等待期间可以保证线程进入休眠状态，不占用CPU资源，同时监听所注册的事件。

在 Android 2.2 及之前，使用 Java wait / notify 进行等待，在 2.3 以后，使用 epoll 机制，为了可以同时处理 native 侧的消息。
更详细一点，为了处理键盘鼠标轨迹球等输入设备的消息。Android基于linux内核，各种输入设备被抽象成了文件，监控这些文件描述符的I/O事件不正是epoll的拿手好戏么。

为什么使用epoll而不是poll？
>因为效率高，epoll时间复杂度是O1,而poll是On

# IdleHandler
> IdleHandler是一个回调接口，可以通过MessageQueue的addIdleHandler添加实现类。
  当MessageQueue中的任务暂时处理完了（没有新任务或者下一个任务延时在之后），这个时候会回调这个接口.
  返回false，那么就会移除它，返回true就会在下次message处理完了的时候继续回调。
