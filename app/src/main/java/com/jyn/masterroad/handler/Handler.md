# Handle
**问：`handle`的作用？**
1. 在Android系统中需要一个切换线程的工具。
2. 同时需要在某些情况下进行delay操作。

**问：`handle`的三种使用方式？**
1. 给`Message`里添加一个`callback`参数。
   ```
    Handler handler = new Handler();
    Message.obtain(handler, new Runnable() {
        @Override
        public void run() {
                
        }
    });
   ```
2. 初始化`Handler`的时候带上一个`Callback`参数。
   ```
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            return false; //返回true的时候，不会执行Handler的handleMessage()方法
        }
    });
   ```
3. 重写`Handler`的`handleMessage()`方法。
   ```
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };
   ```

**问：三种`Message`的优先级**  
&emsp;&emsp; **答：**`Message.callback` > `Handler.Callback.handleMessage` > `Handler.handleMessage`
&emsp;&emsp;**总结：** 消息`callback` > 形参`callback` > `Handler`本身

**问：`Handler.Callback.handleMessage`的返回值有什么影响**
&emsp;&emsp;答：返回`true`的时候，不再执行`handle`本身的`handleMessage`分发方法。

```
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
**总结** :`Handler`其实就是一个发送器/接收器，用来将`Message`发送到`MessageQueue`中，当消息被处理之后会通过`Message.target`回调至`Handler`中进行处理。
1. `Handler`对`Message`设置`delay`，同时将自己`(Handler.this)`设置为`Message`的`target`，并将其添加至`MessageQueue`中。
2. `Looper`不断循环从`MessageQueue`中取出`Message`，并根据前面提到的优先级决定是否回调`Message.target`对应的`Handler`。
3. 若回调至`Handler`则由`Handler`的`Callback`或`handleMessage(Message)`方法进行处理。

## Message
1. 可以携带`Runnable`回调，被优先处理并拦截`Handler`的处理方法
2. `target`属性持有了`Handler`引用，可在`message`分发时回调到特定的`Handler`


# 消息屏障与异步消息

# epoll原理 

# IdleHandler
> IdleHandler是一个回调接口，可以通过MessageQueue的addIdleHandler添加实现类。
  当MessageQueue中的任务暂时处理完了（没有新任务或者下一个任务延时在之后），这个时候会回调这个接口.
  返回false，那么就会移除它，返回true就会在下次message处理完了的时候继续回调。
