[TOC]

Input/Output - 输入输出

# NIO

NIO的buffer可以被操作，强制使用buffer，但是不好用  
非阻塞式，只是支持非阻塞式，默认还是阻塞式，只有网络交互支持非阻塞，文件交互不支持



#### NIO和IO到底有什么区别？有什么关系？
[参考](https://blog.csdn.net/qq_36520235/article/details/81318189)  
1. `NIO`是以块的方式处理数据，但是`IO`是以最基础的字节流的形式去写入和读出的。所以在效率上的话，肯定是`NIO`效率比`IO`效率会高出很多
2. `NIO`不在是和`IO`一样用`OutputStream`和`InputStream`输入流的形式来进行处理数据的，但是又是基于这种流的形式，而是采用了`通道`和`缓冲区`的形式来进行处理数据的。
3. 还有一点就是`NIO`的`通道(Channel)`是可以`双向的`，但是`IO`中的`流(Stream)`只能是`单向的`。
4. 还有就是`NIO`的缓冲区（其实也就是一个字节数组）还可以进行分片，可以建立只读缓冲区、直接缓冲区和间接缓冲区，只读缓冲区很明显就是字面意思，直接缓冲区是为加快 `I/O` 速度，而以一种特殊的方式分配其内存的缓冲区。
5. 补充一点：`NIO`比传统的`BIO`核心区别就是，`NIO`采用的是多路复用的`IO`模型，普通的`IO`用的是阻塞的`IO`模型，两个之间的效率肯定是多路复用效率更高


# Okio
[参考](https://blog.csdn.net/zly921112/article/details/104316110)  
[参考](https://leegyplus.github.io/2020/10/11/Okio/)  

## 1.缓存优化
java io流中的冗余拷贝问题，比较常见的一个使用场景就是复制文件，demo代码如下
```java
//java原生copy文件
File file = new File(Environment.getExternalStorageState(), "test");
try {
    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
    int count = -1;
    byte[] bufferArray = new byte[1024];//临时byte数组
    while ((count = bis.read(bufferArray)) != -1) {
        bos.write(bufferArray, 0, count);
    }
    bis.close();
    bos.close();
} catch (FileNotFoundException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}
```
以`BufferedOutputStream`为例，是利用一个长度为`8192的byte数组`缓存，当要写入数据长度大于缓存最大容量时跳过缓存直接进行io写操作，当写入数据长度大于缓存数组剩余容量的时候先把缓存写入输出流，再将数据写入缓存，其他情况直接写入缓存。

然后原生的输入输出流之间的buffer是没有办法直接建立联系的，输入流中的数据转移到输出流中是：
>文件1 -> 输入流的buf -> 临时byte数组 -> 输出流的buf -> 文件2  

中间经历两次拷贝。

而okio则是用一个Buffer对象去负责缓存，内部维护了一个Segment双向链表，这个Segment则是真正的数据缓存载体，Segment内部利用一个长度为8192的byte数组去缓存，当要写入的数据超过8192的时候则会创建多个Segment对象在链表中有序存储。当Segment使用完毕的时候又会利用SegmentPool进行回收，减少Segment对象创建。


Segment 为双向链表，通过 Segment 引用可以访问它的前继、后驱 Segment 节点，同时 Segment 中持有实际的 ByteArray 的缓存区

```java
//okio拷贝文件
BufferedSource bufferedSource = Okio.buffer(Okio.source(src));
BufferedSink bufferedSink = Okio.buffer(Okio.sink(dest));
bufferedSink.writeAll(bufferedSource);
bufferedSink.close();
```

对于输入流流到输出流是将输入流Buffer的数据直接转移到输出流Buffer中，转移分为两种情况

1. 整段的Segment数据转移则是直接从输出Buffer中脱链然后插入输出Buffer中，直接修改指针效率非常高
2. 非整段的Segment数据转移是判断输出Buffer最后一个Segment是否写的下，写的下的话是数组拷贝，写不下的话则将输入Segment一分为二，将要转移数据的Segment放第一个，然后按照1方式整段Segment转移到写buffer中
   
并且对于Segment分割的时候有做优化，当需要转移数据量小于1K的时候是通过数组拷贝的方式将数据写到新Segment中，大于1K的时候是共享同一个数组，只是修改pos和limit来控制读取区间。

## 2. 超时反馈
原生进行io操作的时候是阻塞式的，没有超时的处理，除非发生异常才会停止，okio增加了超时处理，推出了`Timeout`机制，提供了同步超时和异步超时处理。

1. 同步超时`Timeout`
   >在每次进行读写前检测上一次的读写是否超时，如果超时则抛出异常，那如果检测完之后操作阻塞了很久是没法停止的，只有等到下一次操作的时候才会抛出异常停止操作。

2. 异步超时`AsyncTimeout`
   >在每次要进行读写操作的时候创建一个`AsymcTimeout`对象，然后通过一个`链表存储`，根据即将超时时间排序，快要超时的排在链表头部，然后启动一个Watchdog线程进行检测，优先从链表头部取出AsyncTimeout判断是否超时，超时了的话则调用AsyncTimeout#timeout()方法。okio给用socket创建流提供了默认实现，timeout的时候直接关闭Socket。
