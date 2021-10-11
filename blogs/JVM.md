[TOC]
# 一、JMM - 内存模型

### 栈帧
一个栈帧包含四个部分 [参考](https://zhuanlan.zhihu.com/p/45354152)
#### 1. 局部变量表
> 局部变量表(Local Variable Table)是一组变量值存储空间，用于存放方法参数和方法内定义的局部变量。
> 局部变量表的容量以变量槽(Variable Slot)为最小单位，Java虚拟机规范并没有定义一个槽所应该占用内存空间的大小，但是规定了一个槽应该可以存放一个32位以内的数据类型。
>
> 在Java程序编译为Class文件时,就在方法的Code属性中的max_locals数据项中确定了该方法所需分配的局部变量表的最大容量。(最大Slot数量)
> 一个局部变量可以保存一个类型为boolean、byte、char、short、int、float、reference和returnAddress类型的数据。
> reference类型表示对一个对象实例的引用。returnAddress类型是为jsr、jsr_w和ret指令服务的，目前已经很少使用了。
>
> 虚拟机通过索引定位的方法查找相应的局部变量，索引的范围是从0~局部变量表最大容量。如果Slot是32位的，则遇到一个64位数据类型的变量(如long或double型)，则会连续使用两个连续的Slot来存储。

#### 2. 操作数栈
> 主要用于保存计算过程的中间结果，同时作为计算过程中变量临时的存储空间。
> 不同于程序计数器，Java虚拟机没有寄存器，程序计数器也无法被程序指令直接访问。Java虚拟机的指令是从操作数栈中而不是从寄存器中取得操作数的，因此它的运行方式是基于栈的而不是基于寄存器的。
> 虽然指令也可以从其他地方取得操作数，比如从字节码流中跟随在操作码（代表指令的字节）之后的字节中或从常量池中，但是主要还是从操作数栈中获得操作数。

#### 3. 动态链接
> 在一个class文件中，一个方法要调用其他方法，需要将这些方法的符号引用转化为其在内存地址中的直接引用，而符号引用存在于方法区中的运行时常量池。
> Java虚拟机栈中，每个栈帧都包含一个指向运行时常量池中该栈所属方法的符号引用，持有这个引用的目的是为了支持方法调用过程中的动态连接(Dynamic Linking)。
> 这些符号引用一部分会在类加载阶段或者第一次使用时就直接转化为直接引用，这类转化称为静态解析。另一部分将在每次运行期间转化为直接引用，这类转化称为动态连接。

#### 4. 方法返回地址

# 二、GC - 垃圾回收器
## 1. CMS

## 2. G1


[【318期】面试官：说说堆、栈和字符串常量池它们之间的关系](https://mp.weixin.qq.com/s/GuSg4R7Asi432jmn7gWOag)
[终于搞懂了java8的内存结构，再也不纠结方法区和常量池了！](https://mp.weixin.qq.com/s/56nh1H4MYR6HRX0wayaENA)
[JVM 内存区域划分](https://mp.weixin.qq.com/s/NaCFDOGuoHkfQZZjvY66Jg)
[面试官问为什么新生代不用标记清除算法](https://mp.weixin.qq.com/s/qGL36Q1npiYKKTOG5SVv1A)
[图文详解Java对象内存布局](https://mp.weixin.qq.com/s/qELVigGAxHCXvQ36XIFQUQ)
[Java历史上有三次破坏双亲委派模型，是哪三次？](https://mp.weixin.qq.com/s/nC7yo8Cdsnlzc58UEwxs8g)
[用了很多年的 CMS 垃圾收集器，终于换成了 G1，真香！！](https://mp.weixin.qq.com/s/LpbOUW8VSFsStUkpRayOAA)
[动图图解GC算法 - 让垃圾回收动起来！](https://mp.weixin.qq.com/s/DvPaMfn7xEKIilv-_Ojk8g)