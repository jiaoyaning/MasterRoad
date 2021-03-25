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
&emsp;&emsp;`Message.callback` > `Handler.Callback.handleMessage` > `Handler.handleMessage`

**问：`Handler.Callback.handleMessage`的返回值有什么影响**


# MessageQueue

# Looper

# epoll原理 

延时消息