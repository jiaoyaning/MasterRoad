> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [blog.csdn.net](https://blog.csdn.net/qiang_xi/article/details/75379321)

[TOC]
*   @Bindable
*   @BindingAdapter
*   @BindingBuildInfo
*   @BindingConversion
*   @BindingMethod
*   @BindingMethods
*   @InverseBindingAdapter
*   @InverseBindingMethod
*   @InverseBindingMethods
*   @InverseMethod
*   @Untaggable

以上就是 DataBinding 中所有的注解，一共 11 个注解，其中 @BindingBuildInfo 与 @Untaggable 这两个注解是 hide 的，除了这两个之外，其他 9 个注解在我们日常开发中都有可能用到，但是最常用的只有如下 2 个注解：
- @Bindable
- @BindingAdapter

本文会讲解所有的注解，而 @BindingAdapter 注解的使用又相对较难，所以会着重讲解，其他注解也都会有不同深度的讲解。

#### 1. @BindingBuildInfo

作用：在 dataBinding 生成相关代码时，该注解用来生成相关的 databinding 信息，信息中包含一个 buildId 字段。  
在源码中也能找到 BindingBuildInfo 类的身影：

```
/**
 * 路径：android.databinding.tool.LayoutXmlProcessor
 */
public void writeEmptyInfoClass() {
    Class annotation = BindingBuildInfo.class;
    String classString = "package android.databinding.layouts;\n\nimport " + annotation.getCanonicalName() + ";\n\n@" + annotation.getSimpleName() + "(buildId=\"" + this.mBuildId + "\")\npublic class " + "DataBindingInfo" + " {}\n";
    this.mFileWriter.writeToFile("android.databinding.layouts.DataBindingInfo", classString);
}
```

#### 2.@Untaggable

作用：在 dataBinding 中，通过为 view 设置 tag 的方式来标记一个 view，然后 dataBinding 生成代码时，会根据这些 tag 找到对应的 view，然后设置相关的属性。@Untaggable 注解有一个 String 数组，用来存储相关的 viewType，在生成相关代码时，dataBinding 会从该数组中查找 viewType，判断该 view 是否可设置 tag。

> 难道还有 DataBinding 不支持的操作？那必须的，在 fragment 标签中就不支持 dataBinding 相关的开发方式，一用就会抛异常的，自然不能给 fragment 这种 viewType 设置 tag。

#### 3.@BindingConversion

*   作用于方法
*   被该注解标记的方法，被视为 dataBinding 的转换方法。
*   方法必须为公共静态（public static）方法，且有且只能有 1 个参数

问：那到底什么属性要被转换？又要把属性转换成什么呢？  
答：举个例子，比如 View 的`android:background=""`属性, 属性值为 drawable 类型的对象，如果你要放置一个`@color/black`的属性值，在不使用 dataBinding 的情况下没有任何问题，但是如果你要使用了 dataBinding 方式开发，则会报错，原因在于`android:background=""`的目标值类型为 drawable，而 databinding 会把`@color/black`先解析成 int 类型的颜色值，这时如果把 int 类型的颜色值直接赋值到目标为 drawable 类型的参数中去，那绝逼是要报错的，如果我们可以在 int 类型的颜色值赋值之前，让 int 类型的颜色值自动转换为 colorDrawable 对象，就可以解决这个问题，而 @BindingConversion 注解就是干这个事的。  
我们先来看一个官网上的示例：

```
<View
   android:background="@{isError ? @color/red : @color/white}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```

如果只有上面那段代码，那肯定会报错的，原因上面也说了，所以我们需要定义一个转换方法，把整型的颜色值转换为 drawable 对象：

```
@BindingConversion
public static ColorDrawable convertColorToDrawable(int color) {
   return new ColorDrawable(color);
}
```

这时再运行就不会出错了，因为在 int 型颜色值赋值之前已经被自动转换为 ColorDrawable 了。  
肯定有人会有疑问：
- 为什么我没有定义 convertColorToDrawable 这样的方法，直接写完布局文件就运行也没有报错呢？
- convertColorToDrawable 这个方法是什么时机被调用的呢？

首先之所以没有报错的原因，其实是因为 convertColorToDrawable 这个方法在 dataBinding 的 jar 包中已经帮我们定义好了，我们不用再定义了，所以不会报错。源码路径为：`android.databinding.adapters.Converters`。  
下面再说说调用时机：Android 中的每个 xml 中的属性其实都对应着相应的 java 方法的，如果在 xml 中设置的属性值的类型与对应的 Java 方法的参数类型不符，这时 dataBinding 就会去寻找可以让属性值转换为正确类型的方法，而寻找的根据就是所有被 @BindingConversion 注解标记的方法，这时 convertColorToDrawable 方法就会被调用了。

> 如果我们自己定义了一个功能相同的 convertColorToDrawable 方法，那么 dataBinding 会优先使用我们自己定义的方法。  
> 如果我们自己定义了多个功能相同的 convertColorToDrawable 方法，比如 convertColorToDrawable01，convertColorToDrawable02，convertColorToDrawable03，dataBinding 会选择顺序靠后的方法去使用，说起来很抽象，举个例子：

```
public class CustomConversion {
    @BindingConversion
    public static ColorDrawable convertColorDrawable01(int color) {
        return new ColorDrawable(color);
    }

    @BindingConversion
    public static ColorDrawable convertColorToDrawable02(int color) {
        return new ColorDrawable(color);
    }

    @BindingConversion
    public static ColorDrawable convertColorToDrawable03(int color) {
        return new ColorDrawable(color);
    }
}
```

如上代码所示，convertColorToDrawable03 方法排在最后面，所以 dataBinding 会选择 convertColorToDrawable03 去使用。  
**但是个人强烈建议不要定义功能重复的方法，你这不是闲的蛋疼吗**  
但是话说回来，如果我们真的需要定义多个参数类型相同，返回值类型也相同但是方法体中的实现却各不相同的方法，我们希望 dataBinding 能分辨出方法之间的不同，但是很不幸的是，至少目前来说，dataBinding 是看不出这些方法的区别的，所以还是会选择顺序靠后的那个方法，即便我们真正想让它调用的是其他方法，所以这也算是 DataBinding 的一个不足之处吧。

#### 4.@BindingMethod 与 @BindingMethods

*   @BindingMethods 注解一般用于标记类
*   @BindingMethod 注解需要与 @BindingMethods 注解结合使用才能发挥其功效
*   用法极其简单，但是使用场景很少（因为大多数场景，dataBinding 已经帮我们做好了）

问：@BindingMethod 与 @BindingMethods 存在的意义？  
答：为了说明 @BindingMethod 与 @BindingMethods 存在的意义，我强行写了一个例子：

```
...

 <import type="android.view.Gravity"/>

 <TextView
    android:layout_width="match_parent"
    android:layout_height="55dp"
    android:gravity="@{Gravity.CENTER}"/>

...
```

在`android:gravity="@{Gravity.CENTER}"`属性中我使用了 dataBinding 表达式设置文本对齐方式，在编译时，dataBinding 会根据属性名`gravity`到 TextView 源码中寻找对应的`setGravity()`方法, 然后把我们的`Gravity.CENTER`值设置进去。生成的代码如下：

```
 this.mboundView1.setGravity(android.view.Gravity.CENTER);

```

因为 TextView 中存在目标对象`setGravity()`且参数类型和返回值类型都一样, 所以生成的代码没有任何问题。  
但是如果找不到对应的 set 方法，那么编译器就会报错，告诉我们在某个 View 中找不到相关方法，举个官网上的例子：`android:tint`属性实际对应的方法为`setImageTintList()`, 而不是`setTint()`, 先看如下代码：

```
...

<ImageView
  android:layout_width="match_parent"
  android:layout_height="55dp"
  android:gravity="center"
  android:text="BindingMethodsDemo02"
  android:tint="@{@color/colorAccent}"/>

  ...
```

可以看到，在`android:tint`属性中，我们使用了 dataBinding 表达式，如果不做任何处理，那么 dataBinding 在编译时就会报错，因为在 ImageView 中找不到对应的 setTint() 方法，`android:tint`属性对应的应该是`setImageTintList()`，所以为了让程序能正常运行，我们需要让 dataBinding 能寻找指定的 set 方法来生成代码，即使用`setImageTintList()`方法而不是`setTint()`方法；而 @BindingMethod 与 @BindingMethods 就是用来指定某个 xml 属性对应的 set 方法的。这也就是 @BindingMethod 与 @BindingMethods 注解存在的意义。

> ps: 可能会有人说，我没有做任何处理，直接使用`android:tint="@{@color/colorAccent}"`也没有报错啊，其实是因为 dataBinding 已经帮我们定义好了转换方法了，在你使用`android:tint="@{@color/colorAccent}"`时，已经帮你自动使用`setImageTintList()`方法生成代码了。

说完了 @BindingMethod 与 @BindingMethods 存在的意义，那么 @BindingMethod 与 @BindingMethods 到底怎么使用呢？

##### @BindingMethod

有 3 个字段，这 3 个字段都是必填项，少一个都不行：
- type：要操作的属性属于哪个 View 类，类型为 class 对象，比如：ImageView.class
- attribute：xml 属性，类型为 String ，比如：”android:tint”
- method：指定 xml 属性对应的 set 方法，类型为 String，比如：”setImageTintList”

组合起来如下：

```
@BindingMethod(type = ImageView.class, attribute = "android:tint", method = "setImageTintList")
```

作用就是：在为 ImageView 的 android:tint 属性生成代码时，使用 setImageTintList() 方法生成。

##### @BindingMethods

因为 @BindingMethod 注解不能单独使用，必须要结合 @BindingMethods 才能发挥其功效，所以 @BindingMethods 注解其实就是一个容器，它内部有一个 BindingMethod 数组，存放的是一个一个的 BindingMethod。

@BindingMethod 与 @BindingMethods 结合起来使用的代码示例如下：

```
 @BindingMethods({
        @BindingMethod(type = android.widget.ImageView.class, attribute = "android:tint", method = "setImageTintList"),
        @BindingMethod(type = android.widget.ImageView.class, attribute = "android:tintMode", method = "setImageTintMode"),
})
public class ImageViewBindingAdapter {}
```

这样就完了吗？对，没错， @BindingMethod 与 @BindingMethods 就是这样用的，是不是很简单？定义完以上的代码之后，在编译时，如果你使用到了上面指定的某个属性，且在属性值中使用了 dataBinding 表达式，那么在生成代码时，dataBinding 会自动选择指定的方法去生成。

> ImageViewBindingAdapter 路径为：android.databinding.adapters.ImageViewBindingAdapter

#### 5.@Bindable

*   该注解用于双向绑定，需要与 notifyPropertyChanged() 方法结合使用
*   该注解用于标记实体类中的 get 方法或 “is” 开头的方法, 且实体类必须继承 BaseObserable.
*   用法极其简单

> 实体类也可以不用继承 BaseObservable，而是实现 Observable 接口，但是需要自行处理一些接口方法逻辑，BaseObservable 是实现 Observable 接口的类，内部已经做好了相关逻辑处理，所以选择继承 BaseObservable 相对简单一些。

**示例用法**

```
public class User extends BaseObservable {

    private String name;
    private int age;
    private String sex;
    private boolean isStudent;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.qiangxi.databindingdemo.BR.name);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(com.qiangxi.databindingdemo.BR.age);
    }

    @Bindable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        notifyPropertyChanged(com.qiangxi.databindingdemo.BR.sex);
    }

    @Bindable
    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
        notifyPropertyChanged(com.qiangxi.databindingdemo.BR.student);
    }

    @Bindable({"name", "age", "sex", "isStudent"})
    public String getAll() {
        return "姓名:" + name + ",年龄=" + age + "，性别：" + sex + "，是不是学生=" + isStudent;
    }
}
```

既然用法如此简单，那就不多说了。

##### @Bindable 注解是用来干什么的？

使用 @Bindable 注解标记的 get 方法，在编译时，会在 BR 类中生成对应的字段，然后与 notifyPropertyChanged() 方法配合使用，当该字段中的数据被修改时，dataBinding 会自动刷新对应 view 的数据，而不用我们在拿到新数据后重新把数据在 setText() 一遍，就凭这一点，dataBinding 就可以简化大量的代码. 具体在代码中如何使用双向绑定，在下一篇文章【DataBinding 使用教程（四）：BaseObservable 与双向绑定】会有详细的使用示例，这里就不赘述了。

#### 6.@BindingAdapter

*   用于标记方法, 方法必须为公共静态方法
*   方法的第一个参数的类型必须为 View 类型，不然报错
*   用来自定义 view 的任意属性

```
@Target(ElementType.METHOD)
public @interface BindingAdapter {
    String[] value();
    boolean requireAll() default true;
}
```

上面是源码中`@BindingAdapter`注解的定义，可以看到：
- value 属性是一个 String 数组，用来存放自定义的属性，示例：`android:onItemClick`，`app:onItemClick`
- requireAll 是一个布尔值，用来表示定义的所有属性是否必须都要使用。

完整示例：

```
 @BindingAdapter(value = {"android:onItemClick", "android:onLoadMore","android:loadMoreEnable"}, requireAll = false)
```

在上面的代码中，我们定义了 3 个属性，`requireAll=false`代表我们在使用时，可以只使用其中一个属性，也可以三个属性都使用；如果`requireAll=true`，代表我们定义的这三个属性都是必须要使用的，不然就会报错。

我们知道，默认情况下，如果某个 View 没有`android:xxx=""`或者`app:xxx=""`属性，那么我们在布局文件中是使用不了这些属性的，因为我们根本没有定义这些属性，所以使用不了。但在 dataBinding 的世界中，即便我们没有定义这些属性，我们依然可以通过`android:xxx=""`或者`app:xxx=""`的方式使用这些属性，只要对应的 java 方法中有`setXxx()`方法即可。说起来可能很抽象，我们举个例子：

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <import type="android.text.method.LinkMovementMethod"/>
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="55dp"
            android:gravity="center"
            android:text="BindingMethodsDemo"
            app:movementMethod="@{LinkMovementMethod.getInstance()}"/>
    </LinearLayout>
</layout>
```

在 TextView 中，只有`setMovementMethod()`方法，而没有`android:movementMethod=""`属性，但从上面的代码可以看到，我们通过`app:movementMethod=""`的方式可以直接使用`movementMethod`属性，之所以可以这么做，是因为 dataBinding 支持这么做，这也是 dataBinding 的特性之一，在 dataBinding 中，在编译时，dataBinding 会根据属性名`movementMethod`从 TextView 源码中寻找 setMovementMethod() 方法，并放入对应的参数，而在 TextView 中正好存在 setMovementMethod() 方法且参数类型也一样，这样就实现了即便 android 默认没有提供相关属性，但是只要有相关 set 方法，我们就可以使用对应属性, 这个特性在上面说到`@BindingMethod与@BindingMethods`时也提到过，是不是很爽很刺激呢？

如果你感觉还不够刺激，我们来个更刺激的：
- 如果一个 view 既没有`android:xxx=""`或者`app:xxx=""`属性，也没有`setXxx()`方法，我们通过`@BindingAdapter`同样可以实现自定义`android:xxx=""`或者`app:xxx=""`属性，然后使用。

如果你感觉还是不够刺激，我们还有终极大招：
- 如果我们要使用其他类中的方法，这些方法肯定是不属于这个 View 的，我们可以通过`@BindingAdapter`自定义一个或一些属性，让我们可以在这个 view 中使用属性来操作其他类中的方法。一个最典型的使用场景就是 RecyclerView 与 adapter，通过`@BindingAdapter`，我们可以把 adapter 中的方法直接配置到 RecyclerView 的属性中去，就像这样：

```
<android.support.v7.widget.RecyclerView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:loadMoreEnable="@{true}"
    android:onItemClick="@{presenter.onItemClick}"
    android:onLoadMore="@{presenter.onLoadMore}"
    app:adapter="@{adapter}"
    app:layoutManager="LinearLayoutManager"/>
```

理论总是枯燥的，下面通过 2 个示例来演示如何使用`@BindAdapter`。

##### 示例一:

为 ImageView 设置 url,placeHolder,errorDrawable
1. 使用`@BindingAdapter`定义相关属性：  
   这里要注意的是：定义的属性的顺序与方法中参数的顺序必须保持一致，且方法中第一个参数必须是要操作的 View。

```
public class ImageViewBindingAdapter {

    /**
     * 为imageView设置url,placeHolder,error
     */
    @BindingAdapter(value = {"android:imageUrl", "android:placeHolder", "android:error"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Drawable placeHolder, Drawable error) {
        ImageLoader.bind(imageView, url, placeHolder, error, true, DiskCacheStrategy.ALL, null);
    }
}
```

1.  使用自定义的属性  
    使用自定义属性的时候，对顺序没有要求，只要保证传入的值的类型与`loadImage()`方法中对应参数的类型一致即可。

```
...

<variable
    
    type="String"/>

<ImageView
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:placeHolder="@{@drawable/placeholder}"
    android:imageUrl="@{url}"
    android:error="@{@drawable/error}"/>

...
```

##### 示例二：

为 RecyclerView 设置 adapter，这里 adapter 使用 [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper), 一方面，主要是 BaseRecyclerViewAdapterHelper 可以额外设置一些方法，比如：`setOnItemClickListener`,`setOnLoadMoreListener`,`setEnableLoadMore`等，在演示`@BindingAdapter`用法时能更加说明问题；另一方面，也想凸显出简化代码的幅度，当 BaseRecyclerViewAdapterHelper 结合 DataBinding 之后，你会发现写 adapter 变得更加简单，就像下面这样：

```
public class OrderListAdapter extends BaseQuickAdapter<OrderDetailInfo, BaseViewHolder> {

    public OrderListAdapter() {
        super(R.layout.item_order_list_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailInfo item) {
        ItemOrderListLayoutBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.setOrderInfo(item);
        binding.executePendingBindings();
    }
}
```

1.  使用`@BindingAdapter`定义相关属性：  
    我们针对`setOnItemClickListener`,`setOnLoadMoreListener`,`setEnableLoadMore`这三个方法定义三个属性：

```
public class RecyclerViewBindingAdapter {

    @BindingAdapter(value = {"android:onItemClick", "android:onLoadMore",
            "android:loadMoreEnable"}, requireAll = false)
    public static void setupAdapter(RecyclerView recyclerView, final ItemClickListener itemClickListener,
                                    final LoadMoreListener loadMoreListener, final boolean loadMoreEnable) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null || !(adapter instanceof BaseQuickAdapter)) {
            return;
        }
        BaseQuickAdapter quickAdapter = (BaseQuickAdapter) adapter;
        quickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                itemClickListener.onItemClick(adapter, view, position);
            }
        });

        quickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMoreListener.onLoadMore();
            }
        }, recyclerView);
        quickAdapter.setEnableLoadMore(loadMoreEnable);
        quickAdapter.setLoadMoreView(new RVLoadMoreView());
        quickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    public interface ItemClickListener {
        void onItemClick(BaseQuickAdapter adapter, View view, int position);
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}

```

1.  使用自定义的属性

```
<android.support.v7.widget.RecyclerView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:loadMoreEnable="@{true}"
    android:onItemClick="@{presenter.onItemClick}"
    android:onLoadMore="@{presenter.onLoadMore}"
    app:adapter="@{adapter}"
    app:layoutManager="LinearLayoutManager"/>
```

通过上面的方式，我们就实现了通过在 RecyclerView 中配置属性达到为 adapter 设置点击监听，上拉加载监听，以及是否开启监听的目的。

> 其中的`app:adapter="@{adapter}"`是因为 RecyclerView 有 setAdapter 方法，结合 databinding 的特性，故而可以这样写。而`app:layoutManager="LinearLayoutManager"`属性是 RecyclerView 自己提供的一个属性，为了方便我们为 RecyclerView 设置 layoutManager，其内部采用反射构造一个目标 layoutManager，然后通过 RecyclerView 的`public void setLayoutManager(LayoutManager layout)`再进行设置。

##### 自定义的属性是如何被调用的

我们以 ImageView 设置 url,placeHolder,errorDrawable 为例进行说明。把代码粘过来，防止大家再拉回去看代码：

```
...

<variable
    
    type="String"/>

<ImageView
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:placeHolder="@{@drawable/placeholder}"
    android:imageUrl="@{url}"
    android:error="@{@drawable/error}"/>

...
```

在编译时，dataBinding 扫描 ImageView 中使用 dataBinding 表达式的属性【扫描的依据是属性值是不是以`@{`开头的】，然后找到了`android:placeHolder="@{@drawable/placeholder}"`,`android:imageUrl="@{url}"`,`android:error="@{@drawable/error}"`这 3 个属性，然后判断这 3 个属性是不是 ImageView 的自有属性，如果是自有属性，也会去找对应的 set 方法生成代码；如果不是自有属性，会先找有没有对应且合适的 set 方法，有的话，就用 set 方法生成代码，没有的话，就会从所有定义的 BindingAdapter 中去找合适的方法，显然，dataBinding 会找到这段代码：

```
public class ImageViewBindingAdapter {

    @BindingAdapter(value = {"android:imageUrl", "android:placeHolder", "android:error"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Drawable placeHolder, Drawable error) {
        ImageLoader.bind(imageView, url, placeHolder, error, true, DiskCacheStrategy.ALL, null);
    }
}
```

找到之后，就会调用我们自定义的 loadImage 方法，然后把相关参数放进去，这里就要求我们定义的属性与方法中的参数的顺序必须一致，这样 dataBinding 在生成代码时就知道`android:imageUrl`的属性值应该放到参数的第二个位置，`android:placeHolder`属性值应该放到参数的第三个位置，`android:error`属性值应该放到参数的第四个位置。

dataBinding 生成的代码如下所示：

```
...

com.qiangxi.databindingdemo.databinding.adapter.ImageViewBindingAdapter.loadImage(this.mboundView3, url, getDrawableFromResource(mboundView3, R.drawable.placeholder), getDrawableFromResource(mboundView3, R.drawable.error));

...
```

#### 7.@InverseBindingAdapter

*   作用于方法，方法须为公共静态方法。
*   方法的第一个参数必须为 View 类型，如 TextView 等
*   用于双向绑定
*   需要与 @BindingAdapter 配合使用

```
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface InverseBindingAdapter {

    String attribute();

    String event() default "";
}
```

*   attribute：String 类型，必填，表示当值发生变化时，要从哪个属性中检索这个变化的值，示例：`"android:text"`
*   event： String 类型，非必填；如果填写，则使用填写的内容作为 event 的值；如果不填，在编译时会根据 attribute 的属性名再加上后缀 “AttrChanged” 生成一个新的属性作为 event 的值，举个例子：attribute 属性的值为”android:text”，那么默认会在”android:text”后面追加”AttrChanged”字符串，生成”android:textAttrChanged”字符串作为 event 的值.
*   **event 属性的作用：** 当 View 的值发生改变时用来通知 dataBinding 值已经发生改变了。开发者一般需要使用`@BindingAdapter`创建对应属性来响应这种改变。

##### @InverseBindingAdapter 的简单示例

```
@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
public static String getTextString(TextView view) {
    return view.getText().toString();
}
```

关于什么叫双向绑定以及 @InverseBindingAdapter 更加深入的用法，在下一篇文章【DataBinding 使用教程（四）：BaseObservable 与双向绑定】中会有详细的说明，这里就不赘述了。

#### 8.@InverseBindingMethod 与 @InverseBindingMethods

*   @InverseBindingMethods 注解用于标记类
*   @InverseBindingMethod 注解需要与 @InverseBindingMethods 注解结合使用才能发挥其功效
*   用法极其简单
*   用于双向绑定
*   @InverseBindingMethods 需要与 @BindingAdapter 配合使用才能发挥功效

用法示例：

```
@InverseBindingMethods({
        @InverseBindingMethod(type = SeekBar.class, attribute = "android:progress"),
})
public class SeekBarBindingAdapter {}
```

##### @InverseBindingMethod

```
@Target(ElementType.ANNOTATION_TYPE)
public @interface InverseBindingMethod {

    Class type();

    String attribute();

    String event() default "";

    String method() default "";
}
```

*   type:Class 类型，必填，如：SeekBar.class
*   attribute：String 类型，必填，如：android:progress
*   event：String 类型，非必填，属性值的生成规则以及作用和 @InverseBindingAdapter 中的 event 一样。
*   method：String 类型，非必填，对于什么时候填什么时候不填，这里举个例子说明：比如 SeekBar，它有`android:progress`属性，也有`getProgress`方法【你没看错，就是`getProgress`，不是`setProgress`】，所以对于 SeekBar 的`android:progress`属性，不需要明确指定 method，因为不指定 method 时，默认的生成规则就是前缀 “get” 加上属性名，组合起来就是`getProgress`，而刚才也说了，`getProgress`方法在 seekBar 中是存在的，所以不用指定 method 也可以，但是如果默认生成的方法`getXxx`在 SeekBar 中不存在，而是其他方法比如`getRealXxx`, 那么我们就需要通过 method 属性，指明`android:xxx`对应的 get 方法是`getRealXxx`, 这样 dataBinding 在生成代码时，就使用`getRealXxx`生成代码了；从宏观上来看，`@InverseBindingMethod`的 method 属性的生成规则与`@BindingMethod`的 method 属性的生成规则其实是类似的。

关于 @InverseBindingMethod 与 @InverseBindingMethods 更加具体的用法在下一篇文章【DataBinding 使用教程（四）：BaseObservable 与双向绑定】中会有说明，这里同样就不再赘述了。

#### 9.@InverseMethod

*   作用于方法,
*   用于双向绑定
*   用法较简单

`@InverseMethod` 注解是一个相对独立的注解，不需要其他注解的配合就可以发挥它强大的作用，它的作用就是为某个方法指定一个相反的方法。它有一个 String 类型的必填属性：value，用来存放与当前方法对应的相反方法。

**正方法与反方法的要求：**
- 正方法与反方法的参数数量必须相同
- 正方法的最终参数的类型与反方法的返回值必须相同
- 正方法的返回值类型必须与反方法的最终参数类型相同。

举个例子大家应该就明白了：

```
@InverseMethod("convertIntToString")
public static int convertStringToInt(String value) {
    try {
        return Integer.parseInt(value);
    } catch (NumberFormatException e) {
        return -1;
    }
}
public static String convertIntToString(int value) {
    return String.valueOf(value);
}
```

@InverseMethod 注解在一些特殊的业务场景中，可以大大简化我们的代码，简直爽的不行，在下一篇文章【DataBinding 使用教程（四）：BaseObservable 与双向绑定】中，我会以一个典型示例演示如何使用 @InverseMethod 注解以及它的强大之处。

### 总结：

所有的注解总算是讲完了，关于双向绑定的注解只是讲了基本的用法，具体更详细的用法及使用场景会在下一篇文章中讲解。  
现在终于发现，想写一篇好文章真的太难了，要考虑好多好多东西，生怕自己哪里讲错了误人子弟，就算即便这样，可能这篇文章也还算不上好文章，并且个人能力有限，文章中难免会有讲的错误或者不恰当的地方，还请广大读者不吝指出。

#### 看到有读者要打赏，我反手就是两张收款码

![](https://img-blog.csdn.net/20180329122802905?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FpYW5nX3hp/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)  
![](https://img-blog.csdn.net/20180329122839354?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FpYW5nX3hp/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)