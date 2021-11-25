[TOC]
# 一、什么是inline 内联优化？
`inline` 就是 把函数体复制粘贴到函数调用处。
```kotlin
//kotlin代码
inline fun test() {
    println("I'm a inline function")
}

fun run() { test() }
```
```java
//反编译成Java后
public static final void test() {
    String var1 = "I'm a inline function";
    System.out.println(var1);
}

public static final void run() {
    String var1 = "I'm a inline function";
    System.out.println(var1);
}
```

---

#### **问题：为什么说`inline`可以提高运行效率？**
> JVM 进行方法调用和方法执行依赖 栈帧，每一个方法从调用开始至执行完成的过程，都对应着一个栈帧在虚拟机栈里从入栈到出栈的过程。
> 
> 未内联的情况下，整个执行过程中会产生两个方法栈帧，每一个方法栈帧都包括了 局部变量表、操作数栈、动态连接、方法返回地址和一些额外的附加信息。
> 
> 使用内联的情况下，只需要一个方法栈帧，降低了方法调用的成本。

> **但是:** IDE会提示
>> Expected performance impact from inlining is insignificant. Inlining works best for functions with parameters of functional types. (内联最好与有 Lambda 表达式参数的函数配合使用，这里的内联对性能的影响微乎其微。)

---

#### **问题：为什么`inline`对普通方法无效，对Lambda却有效果？**
> **1. 为什么对普通方法无效？**
>>  `Java` 虽然没有提供 `inline` 类似的关键字，但是 `JVM` 在运行程序的时候会主动进行内联优化。
>>
>> 在这种情况下，对于`超长方法`的`inline`修饰只会徒增字节码长度，并没有什么效果。
> `Kotlin` 标准库中也只修饰了简短方法。
> 
> **2. 为什么Lambda却有效果？**
> > 见第二章

---

# 二、inline 对 Lambda 优化的原理？
首先看一个未进行内联优化的 `Lambda`
```kotlin
//kotlin 代码
fun testLambda(block: () -> Unit){
    println("---before！")
    block()
    println("---after！")
}

fun run1(){
    testLambda { println("---running!") }
}

fun run2(){
    val message = "---running!"
    testLambda { println(message) }
}
```
```java
//反编译成Java后
public static final void testLambda(@NotNull Function0 block) {
    ...
    String var1 = "---before！";
    System.out.println(var1);
    block.invoke();
    var1 = "---after！";
    System.out.println(var1);
}

public static final void run1() {
    testLambda((Function0)null.INSTANCE);
}

public static final void run2() {
    final String message = "---running!";
    testLambda((Function0)(new Function0() {
        public Object invoke() {
            this.invoke();
            return Unit.INSTANCE;
        }

        public final void invoke() {
            String var1 = message;
            System.out.println(var1);
        }
    }));
}
```
此时多出了一个名为 `Function0` 的匿名内部类，这也是 `kotlin` 中 `Lambda` 的原理。
`Function0` 其实就是一个接口，`Lambda` 的本质就是把自己包装成接口来运行。

而且可以看到，如果 `Lambda` 内部如果引用了外部变量 `message` ，每次运行都会 `new` 一个持有外部变量值的 `Function0<Unit>`  对象。

#### **问题：为什么 `kotlin` 要用匿名内部来实现 `Lambda`呢？**
>因为要兼容到 `Java6` ，副作用就是不仅增大了编译代码的体积，也带来了额外的运行时开销。

---

**加入`inline`关键字后的效果**
```kotlin
//kotlin代码
inline fun testLambda(block: () -> Unit){
    println("---before！")
    block()
    println("---after！")
}

fun run1(){
    testLambda { println("---running!") }
}

fun run2(){
    val message = "---running!"
    testLambda { println(message) }
}
```
```java
//反编译成Java代码后
public static final void testLambda(@NotNull Function0 block) {
    ...
    String var2 = "---before！";
    System.out.println(var2);
    block.invoke();
    var2 = "---after！";
    System.out.println(var2);
}

public static final void run1() {
    ...
    String var1 = "---before！";
    System.out.println(var1);
    ...
    String var3 = "---running!";
    System.out.println(var3);
    var1 = "---after！";
    System.out.println(var1);
}

public static final void run2() {
    String message = "---running!";
    ...
    String var2 = "---before！";
    System.out.println(var2);
    ...
    System.out.println(message);
    var2 = "---after！";
    System.out.println(var2);
}
```

`inline` 所修饰的包含 `Lambda` 形参的方法，直接被平移到调用处，相比之前少了一个 `Function0<Unit>`  对象。

这就是 `inline` 关键字对于 `Lambda` 参数的优化。

#### **问题：Java 是如何优化 Lambda 的？**
> 既然 `Kotlin` 的 `Lambda` 存在性能问题，那 `Java` 也必然跑不了
>
> 从 `Java8` 开始， `Java` 借助 `invokedynamic` 指令来完成的 `Lambda` 的优化。
> `invokedynamic`  用于支持动态语言调用。在首次调用时，它会生成一个调用点，并绑定该调用点对应的方法句柄。后续调用时，直接运行该调用点对应的方法句柄即可。
> 
> 说直白一点，第一次调用 `invokedynamic` 时，会找到此处应该运行的方法并绑定， 后续运行时就直接告诉你这里应该执行哪个方法。

---


# noinline 和 crossinline
使一个高阶函数一旦被 `inline` 修饰，它的方法体和所有 `Lambda` 参数都会被内联。

这样就会出现一个问题，我们的 `Lambda` 参数还是不是一个对象？

比如我想把这个 `Lambda` 参数当成对象传递给下一个方法时，或者想提取成全局变量时，由于内联优化破坏了 `Lambda` 本质是匿名对象的结构，这样的需求是无法实现的。

此时就需要 `noinline` 关键字了。

**PS：`noinline` 关键字只能用于修饰形参**

另外，定义了noinline 的lambda表达式将不能直接调用 return

```kotlin
var mBlock: (() -> Unit)? = null

inline fun testLambda(block1: () -> Unit, noinline block2: () -> Unit) {
    block1()

    mBlock = block2 //提取为全局变量
    proxy(block2)   //当做局部变量继续传递
}

fun proxy(block: () -> Unit) {
    println("代理前--")
    block()
    println("代理后--")
}
```

---

`crossinline` 主要是为了阻止 lambda 中有 return 语句(可以有 return@label 语句)。这样可以避免使用 inline 时，lambda 中的 return 影响程序流程。


[重学 Kotlin —— inline，包治百病的性能良药？](https://mp.weixin.qq.com/s/3TQQrl01F6jamMXWjK3f7w)