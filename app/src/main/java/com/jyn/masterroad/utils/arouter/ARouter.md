[TOC]

##### **老版本 ARouter 初始化过慢原因？**
[参考](https://blog.csdn.net/zhujiangtaotaise/article/details/112857966)
> 原本的路由表需要遍历全部dex文件，然后加载dex文件里面的所有class，然后过滤出arouter需要的class文件，
> 这还不是算慢得，如果虚拟机不支持MultiDex还会更慢，它会通过压缩所有的dex文件，然后压缩成zip，
> 然后通过DexFile.loadDex转话成dex文件的集合，我们知道在DexFile.loadDex过程中会把普通的dex文件抓话成odex，
> 这个过程是很慢的
> 
> 使用了`arouter-register`后，直接在编译期把给`LogisticsCenter`的`loadRouterMap`方法注册全部路由表，省去了遍历dex的过程

##### **问题：ARouter 为什么要定义两级的path？一级是group、二级是path**
> 首先apt会根据group来生成对应的路由表，通过group找到对应组的class对象，
> 里面包装的该组下面所有的routeMeta信息，下次如果遇到相同group的路由，则直接去找routeMeta信息
>
> Arouter通过分组包装路由表，有快速查询路由的效率，所以我们可以尽量让Iprovider和Activity、fragment可以放到不同饿组里面，方便我们能快速拿到对应的routeMeta信息
> 
> 总结：为了避免一次性将工程中所有的页面和path之间的映射关系加载到内存中，ARouter采用了分组加载的机制。只有当一个分组中的某一个path被加载到内存中时，该分组中所有的映射关系才会被加载到内存中。

##### **问题：为什么一定要在build.gradle中配置javaCompileOptions？**
> 其实仔细看，我们真正配置的是annotationProcessorOptions，这个呢其实又是annotationProcessor工具的配置参数。
> 不过具体为什么要配置该参数，主要是因为ARouter将每一个module中的path和Activity之间的映射关系保存在一个单独的类中，为了避免类重名，因此类名最后都是以module名结尾，所以为了获得module名，需要我们手动配置该参数。


##### **问题：如果我们注解相同的path会怎么样？**
> 在同一个module下注解相同的path，那么排在字母表后面的元素会无效。即如果有一个SecondActivity使用/a/b的path，而另一个ThirdActivity也使用/a/b的path。那么在路由表中，将不会有ThirdActivity的路由数据
>
> 在不同的module下注解相同的path，由于apt框架是分module编译，并且每个module都会生成ARouter$$Root$module_name,ARouter$$Group$$group_name等文件，
>   那么毫无疑问，会生成两个相同的文件。还是以上面的SecondActivity和ThirdActivity为例子，那么它们都会生成ARouter$$Group$$a的文件，那么在合并到dex的时候肯定会出错，事实上也是这样的。

---

Android ARouter无法跳转
https://www.jianshu.com/p/7dc00007aed8

Arouter全面讲解
https://juejin.cn/post/6895710601079685133
https://juejin.cn/post/6895712042917838861

阿里ARouter问的几个问题你都会嘛？
https://zhuanlan.zhihu.com/p/361025253