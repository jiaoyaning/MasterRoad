# let & with & run & apply & also
| 函数名 | 函数体内使用的对象       | 返回值 | 适用的场景                                 |
| ------ | ------------------------ | ------ | ------------------------------------------ |
| let    | it指代当前对象           | 闭包   | 合并多处判断null的操作                     |
| with   | this指代当前对象，可省略 | 闭包   | 合并多个方法调用                           |
| run    | this指代当前对象，可省略 | 闭包   | let和with的结合体                          |
| apply  | this指代当前对象，可省略 | this   | 能用run的地方就能用apply，多用于初始化对象 |
| also   | it指代当前对象           | this   | 能用let就能用also，可用于函数链式调用      |





 #属性委托 by
 
 一文彻底搞懂Kotlin中的委托
 https://mp.weixin.qq.com/s/dYBYuBAnYRgSiKQrU-ZGpg