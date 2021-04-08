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
&emsp;&emsp;**答：**`Message.callback` > `Handler.Callback.handleMessage` > `Handler.handleMessage`
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
**总结一下** :`Handler`其实就是一个发送器/接收器，用来将`Message`发送到`MessageQueue`中，当消息被处理之后会通过`Message.target`回调至`Handler`中进行处理。


# MessageQueue

# Looper

# epoll原理 

延时消息