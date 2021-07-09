# Schedulers(线程调度器)  
 **1. `AndroidSchedulers.mainThread()`**
   > 需要引用rxandroid, 切换到UI线程  

 **2. `Schedulers.computation()`**  
   >用于计算任务，如事件循环和回调处理，默认线程数等于处理器数量

 **3. `Schedulers.io()`**
   >用于`IO`密集型任务，如异步阻塞`IO`操作，这个调度器的线程池会根据需求，它默认是一个`CacheThreadScheduler`

**4. `Schedulers.newThread()`**   
   >为每一个任务创建一个新线程

**5. `Schedulers.trampoline()`**
   >在当前线程中立刻执行，如当前线程中有任务在执行则将其暂停， 等插入进来的任务执行完成之后，在将未完成的任务继续完成。

**6.`Scheduler.from(executor)`**
   >指定Executor作为调度器

```java
   //Observable.just()执行在新线程
  Observable.just(getFilePath())
           //指定在新线程中创建被观察者
          .subscribeOn(Schedulers.newThread())
          //将接下来执行的线程环境指定为io线程
          .observeOn(Schedulers.io())
            //map就处在io线程
          .map(mMapOperater)
            //将后面执行的线程环境切换为主线程，
            //但是这一句依然执行在io线程
          .observeOn(AndroidSchedulers.mainThread())
          //指定线程无效，但这句代码本身执行在主线程
          .subscribeOn(Schedulers.io())
          //执行在主线程
          .subscribe(mSubscriber);
```
## CompositeDisposable
一个disposable的容器，可以容纳多个disposable，如果这个CompositeDisposable容器已经是处于dispose的状态，那么所有加进来的disposable都会被自动切断