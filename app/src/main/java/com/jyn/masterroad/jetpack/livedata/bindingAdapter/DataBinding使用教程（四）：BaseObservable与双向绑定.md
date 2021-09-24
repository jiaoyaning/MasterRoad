> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [blog.csdn.net](https://blog.csdn.net/qiang_xi/article/details/77586836)

### 引言

这篇文章主要讲两个知识点：

1.  双向绑定：主要是把上一篇文章中的一些注解通过具体实例再详细说一下，通过这些实例，尽量把双向绑定说的清楚一些。
2.  BaseObservable：包括与 BaseObservable 相关的东西。比如：@Bindable 注解，响应式包装类等；

### 什么叫双向绑定

举个例子简单说一下：在 TextView 中，我们通过 dataBinding 把实体中的数据放到 TextView 中展示，这是从实体到 view 方向上的绑定；当 TextView 的数据发生改变时，比如我们手动输入了一些数据，我们通过 dataBinding 把 view 中的数据设置到对应的实体类的字段中，这是从 view 到实体类方向上的绑定，整合起来就是双向绑定。但是双向绑定说起来容易做起来难，为此 dataBinding 提供了一系列以 Inverse 开头的注解，来帮助 DataBinding 自身的整个体系以及开发者可以更好的控制和使用双向绑定。

![](https://github.com/qiangxi/DataBindingDemo/blob/master/image/image08.png?raw=true)

上图是我简单画的一个双向绑定示意图：
- 当左侧数据源（实体类）发生改变时，自动通知右侧 view 刷新数据，把新的数据绑定到 view 中展示。
- 当右侧 view 中的内容改变时，自动通知左侧数据源（实体类）刷新数据，把 view 中新的内容绑定到数据源（实体类）中。

### 双向绑定的问题

根据上面对双向绑定的解释，我们发现两个问题：
- 死循环绑定：因为数据源改变会通知 view 刷新，而 view 改变又会通知数据源刷新，这样一直循环往复，就形成了死循环绑定。
- 数据源中的数据有时需要经过转换才能在 view 中展示，而 view 中展示的内容也需要经过转换才能绑定到对应的数据源上。

### 双向绑定问题的解决方式

1.  死循环绑定的解决方式：

解决方式很简单，举个 dataBinding 源码中的例子，路径：`android.databinding.adapters.TextViewBindingAdapter`：

```
@BindingAdapter("android:text")
public static void setText(TextView view, CharSequence text) {
    final CharSequence oldText = view.getText();
    if (text == oldText || (text == null && oldText.length() == 0)) {
        return;
    }
    if (text instanceof Spanned) {
        if (text.equals(oldText)) {
            return; // No change in the spans, so don't set anything.
        }
    } else if (!haveContentsChanged(text, oldText)) {
        return; // No content changes, so don't set anything.
    }
    view.setText(text);
}
```

从上面的源码可以看到，在处理双向绑定的业务逻辑时，要对新旧数据进行比较，只处理新旧数据不一样的数据，对于新旧数据一样的数据作 return 处理，通过这种方式来避免死循环绑定。

1.  数据源中的数据有时需要经过转换才能在 view 中展示，而 view 中展示的内容也需要经过转换才能绑定到对应的数据源上的解决方式：

有些开发者可能不太了解什么叫数据源中的数据需要经过转换才能在 view 中展示，以及 view 中展示的内容也需要经过转换才能绑定到对应的数据源上，我举个实际开发中的场景：

在一些约车或者外卖等类型的 APP 中，都有订单类型这个字段，以约车 APP 为例，订单有立即单，预约单，接机单等其他订单类型，用户在提交订单后，在用户的订单列表或详情中是可以看到订单类型的，比如 “立即单”, 但是在服务端，存储立即单这个字段的时候，并不是直接存储“立即单” 这几个字的，而是以字典表的形式来存储的，比如 “OT00001” 代表立即单，在开发中，我们肯定不能把 “OT00001” 展示到界面上给用户看吧，但是服务端给我们返回的 json 中就是 “OT00001”，所以我们在接收到“OT00001” 时要把 “OT00001” 转换成 “立即单” 展示到界面上给用户看，这就是数据源中的数据需要经过转换才能在 view 中展示；  
而如果用户修改了订单类型，然后提交到服务端去修改，我们肯定是以 “OT00001” 的形式提交到服务端的，但是用户在输入时却是以 “立即单” 的形式输入的，所以在提交服务端时，我们需要把 “立即单” 转换为 “OT00001” 再去提交到服务端，这就是 view 中展示的内容也需要经过转换才能绑定到对应的数据源上。

如果不使用 dataBinding，这些转换时机以及逻辑都要我们自己掌握，但是使用了 dataBinding 之后，这些操作都变得自动化，在你设置 “OT00001” 时，会自动转换为 “立即单” 在界面上展示，而当你输入 “立即单” 时，对应的实体类字段会自动变为“OT00001”，这会大大节省我们的开发成本。

我们可以通过 @InverseMethod 来做到这种自动化。@InverseMethod 的基本用法已经在上一篇文章中有说明，这里就直接贴出代码：  
**1. 使用 @InverseMethod 定义转换方法**

```
public class InverseMethodDemo {

    @InverseMethod("orderTypeToString")
    public static String stringToOrderType(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "立即单":
                return AppConstants.ORDER_TYPE_1;
            case "预约单":
                return AppConstants.ORDER_TYPE_2;
            case "接机单":
                return AppConstants.ORDER_TYPE_3;
            case "送机单":
                return AppConstants.ORDER_TYPE_4;
            case "半日租单":
                return AppConstants.ORDER_TYPE_5;
            case "全日租单":
                return AppConstants.ORDER_TYPE_6;
            default:
                return null;
        }
    }


    public static String orderTypeToString(String code) {
        if (code == null) {
            return null;
        }
        switch (code) {
            case AppConstants.ORDER_TYPE_1:
                return "立即单";
            case AppConstants.ORDER_TYPE_2:
                return "预约单";
            case AppConstants.ORDER_TYPE_3:
                return "接机单";
            case AppConstants.ORDER_TYPE_4:
                return "送机单";
            case AppConstants.ORDER_TYPE_5:
                return "半日租单";
            case AppConstants.ORDER_TYPE_6:
                return "全日租单";
            default:
                return null;
        }
    }
}

```

**2. 在布局文件中使用**

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <import type="com.qiangxi.databindingdemo.databinding.method.InverseMethodDemo"/>

        <variable
            
            type="String"/>

    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <EditText
            android:id="@+id/textView"
            android:layout_width="match_parent"
            android:layout_height="55dp"
            android:gravity="center_horizontal"
            android:text="@={InverseMethodDemo.orderTypeToString(orderTypeCode)}"/>

    </LinearLayout>
</layout>
```

当使用`mBinding.setOrderTypeCode("OT00001")`时，EditText 中会自动展示 “立即单”，当 EditText 中的内容修改为“预约单” 时，orderTypeCode 字段值会自动变为“OT00002”。

要注意的是：
- 转换方法中要对参数进行判空，不然会引起空指针异常
- 记得使用双向绑定表达式，不然转换方法不起作用，双向绑定表达式的写法为 “@={}”

##### 问：当 EditText 中的内容修改为 “预约单” 时，orderTypeCode 字段值会自动变为“OT00002”。这一步 dataBinding 是如何做到的？

答：当 EditText 的文本内容发生改变时，dataBinding 会收到通知然后调用`TextViewBindingAdapter.getTextString(textView);`拿到 EditText 内容，然后通过 @InverseMethod 注解标记的转换方法`InverseMethodDemo.stringToOrderType("预约单");`拿到对应的编码 “OT00002”，但是此时 orderTypeCode 字段值还没有变为 “OT00002”，为了让 orderTypeCode 字段值变为 “OT00002”，dataBinding 会调用`mBinding.setOrderTypeCode("OT00002")`真正的把”OT00002” 赋值给 orderTypeCode 字段。我把 dataBinding 生成的源码贴出来，大家对照着理解：

```
private android.databinding.InverseBindingListener textViewandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
    @Override
    public void onChange() {
        // Inverse of InverseMethodDemo.orderTypeToString(orderTypeCode)
        //         is com.qiangxi.databindingdemo.databinding.ActivityXmlLabelBinding.this.setOrderTypeCode(InverseMethodDemo.stringToOrderType(callbackArg_0))
        java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(textView);
        // localize variables for thread safety
        // InverseMethodDemo.orderTypeToString(orderTypeCode)
        java.lang.String inverseMethodDemoOrderTypeToStringOrderTypeCode = null;
        // orderTypeCode
        java.lang.String orderTypeCode = mOrderTypeCode;


        if ((com.qiangxi.databindingdemo.databinding.ActivityXmlLabelBinding.this) != (null)) {



            com.qiangxi.databindingdemo.databinding.method.InverseMethodDemo.stringToOrderType(callbackArg_0);

            com.qiangxi.databindingdemo.databinding.ActivityXmlLabelBinding.this.setOrderTypeCode(com.qiangxi.databindingdemo.databinding.method.InverseMethodDemo.stringToOrderType(callbackArg_0));
        }
    }
};
```

但是不知道机智的小伙伴有没有注意到，当 databinding 帮我们调用`mBinding.setOrderTypeCode("OT00002")`时，根据 dataBinding 的绑定机制，肯定又会再次触发 EditText 的内容的改变，会调用`InverseMethodDemo.orderTypeToString(orderTypeCode)`方法把”OT00002”转成 “预约单” 字符串放到 EditText 中展示，但是此时 EditText 中的内容已经是 “预约单” 字符串了，如果我们不做限制，就会造成我们上面说的死循环绑定，所以现在知道死循环绑定的危害了吧，这要求这我们在自定义双向绑定的一些逻辑时，一定要防止死循环绑定，死循环绑定的解决办法上面也说了，只要对比一下新旧内容，如果新旧内容一致，则 return，这样就可以避免死循环绑定。

### @InverseBindingMethod 与 @InverseBindingMethods 实例

`@InverseBindingMethods`注解的作用与`@BindingMethods`类似，都相当于一个容器，只不过`@InverseBindingMethods`用来盛放的是`@InverseBindingMethod`，所以真正发挥双向绑定作用的是`@InverseBindingMethod`注解，下面我们以一个实例的方式来讲解`@InverseBindingMethod`注解。

在上一篇文章中，我们知道：

*   @InverseBindingMethod 注解在属性数量上只是比 @BindingMethod 注解多了一个 String 类型的 event 属性；其实 @InverseBindingMethod 注解的重点也就在于这个 event 属性。event 属性值的生成规则在上一篇文章中详细说过，这里就不再赘述。
*   @InverseBindingMethod 与 @InverseBindingMethods 需要结合 @BindingAdapter 注解才能发挥作用

但是到底该怎么结合 @BindingAdapter 注解呢？换句话说 @BindingAdapter 注解该怎么配合呢？  
我们看一段代码，通过这段代码来讲解：

```
@InverseBindingMethods({
        @InverseBindingMethod(type = RatingBar.class, attribute = "android:rating"),
})
public class RatingBarBindingAdapter {
    @BindingAdapter("android:rating")
    public static void setRating(RatingBar view, float rating) {
        if (view.getRating() != rating) {
            view.setRating(rating);
        }
    }

    @BindingAdapter(value = {"android:onRatingChanged", "android:ratingAttrChanged"},
            requireAll = false)
    public static void setListeners(RatingBar view, final OnRatingBarChangeListener listener,
            final InverseBindingListener ratingChange) {
        if (ratingChange == null) {
            view.setOnRatingBarChangeListener(listener);
        } else {
            view.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (listener != null) {
                        listener.onRatingChanged(ratingBar, rating, fromUser);
                    }
                    ratingChange.onChange();
                }
            });
        }
    }
}
```

上面的代码是 dataBinding 的源码，路径为：`android.databinding.adapters.RatingBarBindingAdapter`。我们从上到下依次分析：

```
@InverseBindingMethods({
        @InverseBindingMethod(type = RatingBar.class, attribute = "android:rating"),
})
```

这段代码定义了 RatingBar 类的`android:rating`属性，但是没有定义 event 和 method 属性，既然没有定义，那么就采用默认值，根据上一篇文章的内容，我们就可以知道：event 的属性值为 “android:ratingAttrChanged”，method 的属性值为 “getRating”。

```
@BindingAdapter("android:rating")
public static void setRating(RatingBar view, float rating) {
    if (view.getRating() != rating) {
        view.setRating(rating);
    }
}
```

这里就是需要 @BindingAdapter 注解配合的一个地方。  
我们知道 RatingBar 中默认已经提供`android:rating`属性了，但是为什么还要用 @BindingAdapter 重复定义一个一模一样的呢？原因就是为了防止死循环绑定。我们在上面通过 @InverseBindingMethod 注解指定了`android:rating`属性需要支持双向绑定，那么自然要防止死循环绑定问题。 当我们通过 @BindingAdapter 定义一个一模一样的`android:rating`属性时，一旦在布局文件中对这个属性使用了 dataBinding 表达式，那么 dataBinding 就会调用这里的 “setRating“` 方法，如果使用的 dataBinding 表达式是双向绑定表达式 “@={}”，那么就可以避免死循环绑定。

```
@BindingAdapter(value = {"android:onRatingChanged", "android:ratingAttrChanged"},
            requireAll = false)
public static void setListeners(RatingBar view, final OnRatingBarChangeListener listener,
        final InverseBindingListener ratingChange) {
    if (ratingChange == null) {
        view.setOnRatingBarChangeListener(listener);
    } else {
        view.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (listener != null) {
                    listener.onRatingChanged(ratingBar, rating, fromUser);
                }
                ratingChange.onChange();
            }
        });
    }
}
```

这里是需要 @BindingAdapter 注解配合的另一个地方。  
在上一篇文章中，我们详细讲解了 @BindingAdapter 的各种用法和使用场景，但是这里的代码和上一篇文章中的示例又略有不同，这里多了一个`"android:ratingAttrChanged"`属性，并且参数中多了一个`InverseBindingListener`类型的参数，从上一篇文章中我们知道，注解中属性的顺序与方法中的参数顺序要保持一致，所以`"android:ratingAttrChanged"`属性对应的就是`InverseBindingListener`类型的参数，而我们也知道`"android:ratingAttrChanged"`属性又是 @InverseBindingMethod 注解中 event 属性的属性值，也就是说 event 属性最终转换成了 InverseBindingListener，而 event 属性就是用来双向绑定时对 view 的变化作出通知的，但是我们一直不知道 event 属性是如何把 view 内容的变化通知出去的，换句形象的话说我们不知道 event 属性是如何通风报信的；现在我们知道了，原来 event 属性转变为 InverseBindingListener 之后，借助 InverseBindingListener 实现了通风报信，但是 InverseBindingListener 又是什么时机去通风报信的呢？我们通过上面的源码可以看到，RatingBar 设置了 OnRatingBarChangeListener 监听，当 RatingBar 的 rating 值改变时，会触发这个监听，这是个绝佳的时机，InverseBindingListener 就瞅准了这个机会，在 onRatingChanged 的回调里把消息通知了出去。

通过上面的方式就实现了当 view 发生改变时，可以同时向外发出通知。但是只有通知的发送还不行，还得有人接收并处理这个通知啊，不然双向绑定的功能不够全面啊。那么 dataBinding 是如何接收并处理通知的呢？我们看一段代码：

```
...

// Inverse Binding Event Handlers
private android.databinding.InverseBindingListener textViewandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
    @Override
    public void onChange() {
        // Inverse of InverseMethodDemo.orderTypeToString(orderTypeCode)
        //         is com.qiangxi.databindingdemo.databinding.ActivityXmlLabelBinding.this.setOrderTypeCode(InverseMethodDemo.stringToOrderType(callbackArg_0))
        java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(textView);
        // localize variables for thread safety
        // InverseMethodDemo.orderTypeToString(orderTypeCode)
        java.lang.String inverseMethodDemoOrderTypeToStringOrderTypeCode = null;
        // orderTypeCode
        java.lang.String orderTypeCode = mOrderTypeCode;

        if ((com.qiangxi.databindingdemo.databinding.ActivityXmlLabelBinding.this) != (null)) {

            com.qiangxi.databindingdemo.databinding.method.InverseMethodDemo.stringToOrderType(callbackArg_0);

            com.qiangxi.databindingdemo.databinding.ActivityXmlLabelBinding.this.setOrderTypeCode(com.qiangxi.databindingdemo.databinding.method.InverseMethodDemo.stringToOrderType(callbackArg_0));
        }
    }
};

...
```

上面的代码直接 new 了一个 InverseBindingListener，我们之前也说了，view 改变的通知就是通过 InverseBindingListener 发送出去的，也就是说现在我们实例化的这个 InverseBindingListener 就是用来接收并处理 view 改变的通知的。但是只是单纯的实例化 InverseBindingListener 还不行，因为还没有任何地方调用 InverseBindingListener 的实例，我们需要把这个 InverseBindingListener 实例设置到它该去的地方，那么什么地方是它该去的地方呢？先看一段代码：

```
...

@BindingAdapter(value = {"android:beforeTextChanged", "android:onTextChanged",
        "android:afterTextChanged", "android:textAttrChanged"}, requireAll = false)
public static void setTextWatcher(TextView view, final BeforeTextChanged before,
        final OnTextChanged on, final AfterTextChanged after,
        final InverseBindingListener textAttrChanged) {
   ...
   ...
   ...
}

...
```

上面的 setTextWatcher 方法中的 InverseBindingListener 类型的参数就是它该去的地方。那么又是什么时机把 InverseBindingListener 送到它该去的地方的呢？我们再看一段代码：

```
...

@Override
protected void executeBindings() {
    ...
    if ((dirtyFlags & 0x8L) != 0) {
        // api target 1
        android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.textView,
        (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null,
        (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null,
        (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null,
        textViewandroidTextAttrChanged);
    }
    ...
}

...
```

可以看到，dataBinding 在执行 executeBindings 方法时，就调用 TextViewBindingAdapter 的 setTextWatcher 方法，把 InverseBindingListener 的实例 textViewandroidTextAttrChanged 设置到了它该去的地方。现在双向绑定的功能才算完整，虽然整个双向绑定的流程是基于 @InverseBindingMethod 与 @InverseBindingMethods 讲解的，但是双向绑定的原理是不变的，@InverseBindingAdapter 也是这样的原理。

### @InverseBindingAdapter 实例讲解

双向绑定原理性的东西在上面说到 @InverseBindingMethod 与 @InverseBindingMethods 时已经详细讲解了， 就不再赘述，这里主要说一下 @InverseBindingAdapter 自己独有的一些特性。我们同样先贴出一份代码，以这份代码为基础进行讲解：

```
public class TabHostBindingAdapter {

    @InverseBindingAdapter(attribute = "android:currentTab")
    public static int getCurrentTab(TabHost view) {
        return view.getCurrentTab();
    }

    @InverseBindingAdapter(attribute = "android:currentTab")
    public static String getCurrentTabTag(TabHost view) {
        return view.getCurrentTabTag();
    }

    @BindingAdapter("android:currentTab")
    public static void setCurrentTab(TabHost view, int tab) {
        if (view.getCurrentTab() != tab) {
            view.setCurrentTab(tab);
        }
    }

    @BindingAdapter("android:currentTab")
    public static void setCurrentTabTag(TabHost view, String tabTag) {
        if (view.getCurrentTabTag() != tabTag) {
            view.setCurrentTabByTag(tabTag);
        }
    }

    @BindingAdapter(value = {"android:onTabChanged", "android:currentTabAttrChanged"},
            requireAll = false)
    public static void setListeners(TabHost view, final OnTabChangeListener listener,
            final InverseBindingListener attrChange) {
        if (attrChange == null) {
            view.setOnTabChangedListener(listener);
        } else {
            view.setOnTabChangedListener(new OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    if (listener != null) {
                        listener.onTabChanged(tabId);
                    }
                    attrChange.onChange();
                }
            });
        }
    }
}
```

在上一篇文章中，我们已经知道 @InverseBindingAdapter 注解是作用于方法的，我们剪一段代码来看：

```
@InverseBindingAdapter(attribute = "android:currentTab")
public static int getCurrentTab(TabHost view) {
    return view.getCurrentTab();
}
```

可以看到 @InverseBindingAdapter 只配置了一个 attribute 属性，String 类型的 event 属性没有配置，那么就使用默认配置：event 的属性值为 “android:currentTabAttrChanged”。  
我们之前也说了，双向绑定中，当 view 改变时要通知对应的数据源刷新，所以数据源的改变肯定要依赖于 view 改变的内容，也就是说当 TabHost 的`android:currentTab`属性值改变时，要获取到`android:currentTab`的属性值，也就是`getCurrentTab`方法，但是这里有点奇怪，因为 TabHost 中已经定义了`getCurrentTab`方法，直接拿着用就行了啊，为什么还要再重复定义一个功能相同的方法呢？其实还是因为双向绑定的机制导致的，如果你使用 @InverseBindingAdapter 的方式指定某个属性要满足双向绑定的需求，那么 dataBinding 在获取属性值的时候，会从 @InverseBindingAdapter 标记的方法中找到一个合适的方法去使用，所以不管原 view 中有没有对应的方法，都需要通过 @InverseBindingAdapter 来标记一个功能相同的方法；但是如果你使用的是 @InverseBindingMethod 的方式指定某个属性要满足双向绑定的需求，则不用这样做。

@InverseBindingAdapter 的其他内容与 @InverseBindingMethod 与 @InverseBindingMethods 中说的也都大同小异了，比如 event 的作用等等；这里就不再赘述了。

至此，上一篇文章中遗留的一些 @inverse 开头的注解也都全部讲完了，我们总结一下：
- 只要自定义双向绑定，都必须要有 @BindingAdapter 注解的参与
- @InverseBindingMethod 与 @InverseBindingMethods + @BindingAdapter 可以实现双向绑定
- @InverseBindingAdapter + @BindingAdapter 也可以实现双向绑定
- @InverseMethod 是一个相对独立的注解，功能强大。

### BaseObservable

BaseObservable + @Bindable + notifyPropertyChanged() 的使用方式在上一篇文章【DataBinding 使用教程（三）：各个注解详解】中已经讲解过，这里就不再赘述。下面说说 BaseObservable 的作用：

BaseObservable 功能的具体表现主要是在数据源发生改变时，自动通知 view 刷新数据。这句话包含 2 个意思：
- BaseObservable 是双向绑定的基础
- 结合双向绑定表达式`@={}`，也可以做到当 view 内容变化时，自动通知数据源更新。

虽然 BaseObservable 可以实现双向绑定，但别以为只靠 BaseObservable 再加上双向绑定表达式`@={}`就可以实现双向绑定，这都是表面现象，实际上在使用双向绑定时，还得依靠各个注解的帮助，毕竟监听 view 内容的变化是 BaseObservable 做不到的，得依赖指定的注解才能把 view 内容的变化通知出去（对于 android 自带的 view，DataBinding 已经帮我们做了），然后 BaseObservable 收到这些通知后触发 notifyPropertyChanged(), 进而改变数据源以及界面。

虽然 BaseObservable 这么 666，但是也有他的局限性，比如：
- 必须要求子类继承 BaseObservable
- 继承之后必须要使用 @Bindable 注解和 notifyPropertyChanged() 方法

这就产生了一些问题：
- 我们的实体类已经继承了其他父类，不能再继承 BaseObservable 了
- 我们的实体类的字段很多很多，要是每个 get/set 方法都对应加上 @Bindable 注解和 notifyPropertyChanged() 方法，那绝壁很难接受。

既然有问题，那就解决问题：
1. 实体类已经继承了其他父类，不能再继承 BaseObservable：  
   既然不能继承 BaseObservable，那就实现 Observable 接口呗，反正 BaseObservable 也是 Observable 接口的实现类。但问题是如何处理实体类中的 get/set 方法以及 Observable 接口中的两个方法：`addOnPropertyChangedCallback`和`removeOnPropertyChangedCallback`。  
   好在 DataBinding 给出了解决方式，那就是`PropertyChangeRegistry`，一个简单的示例如下：

```
public class User implements Observable {
    private PropertyChangeRegistry registry = new PropertyChangeRegistry();

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
        registry.notifyChange(this, BR.name);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        registry.notifyChange(this, BR.age);
    }

    @Bindable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        registry.notifyChange(this, BR.sex);
    }

    @Bindable
    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
        registry.notifyChange(this, BR.student);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.remove(callback);
    }
}
```

可以看到，采用实现 Observable 接口的方式仍然需要为每个 get 方法添加`` `@Bindable``注解，并且只是换了个方式为 set 方法添加 notifyChange。但确确实实解决了问题。

1.  实体类的字段很多很多，为每个 get/set 方法都对应加上 @Bindable 注解和 notifyPropertyChanged() 方法太浪费时间  
    为解决这个问题，DataBinding 提供了响应式对象：

##### 针对 8 种基本类型的数据结构提供了专门的包装类

*   ObservableBoolean
*   ObservableByte
*   ObservableChar
*   ObservableDouble
*   ObservableFloat
*   ObservableInt
*   ObservableLong
*   ObservableShort

##### 针对集合提供的包装类

*   ObservableArrayList
*   ObservableArrayMap

##### 针对实现了 Parcelable 接口的对象提供的包装类

*   ObservableParcelable

##### 针对其他类型提供的包装类

*   ObservableField。最典型的：ObservableField

一个响应式对象的使用示例：

```
public class User02 {
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();
    public final ObservableField<String> sex = new ObservableField<>();
    public final ObservableBoolean isStudent = new ObservableBoolean();
}

```

可以看到，通过使用响应式对象，确确实实不用写 @Bindable 注解和 notifyPropertyChanged() 方法了，甚至连 get/set 方法都省了，看起来一切都很不错，但是响应式对象的问题在于赋值和取值操作变得稍显复杂了，就像下面这样：

```
 User user = new User();
 user.age.set(5);//赋值
 int i = user.age.get();//取值
```

并且把字段定义成 public 形式的，破坏了 Java 的封装性，有些不符合日常编码习惯。

#### 站在巨人的肩膀上

从上面的描述来看，DataBinding 提供的这两种解决方式都很不错，但同样的都或多或少的有些问题，大家视具体情况选择某种方式使用吧。

下面我们针对继承 BaseObservable 方式的双向绑定再多说一些：  
根据上面的描述，我们可以对继承 BaseObservable 方式的双向绑定总结一下：

##### 优点：

*   不会破坏程序的封装性
*   赋值、取值都符合正常编码风格

##### 缺点：

*   当字段比较多时，要为每个 get/set 方法都对应加上 @Bindable 注解和 notifyPropertyChanged() 方法很浪费时间
*   手动添加 @Bindable 注解和 notifyPropertyChanged() 极容易出错，比如 notifyPropertyChanged() 方法中的 BR 字段就容易写错。

如果让每个 get/set 方法都对应**自动**加上 @Bindable 注解和 notifyPropertyChanged() 方法，这会极大的节省我们的开发时间并且不会出错，而达到这个目的最简单直接的方式就是使用插件：  
**小弟不才，编写了一款简单易用的 AndroidStudio 插件 - dataBindingGenerator**，专门用来生成上面说的那些代码，语言总是苍白的，我们来个动图感受一下这个插件的功能，快捷键：`Alt+Insert`；【动图链接失效的话，请点击[这里](https://github.com/qiangxi/ImageLib/blob/master/Image/GIF.gif)看动图】：  
![](https://github.com/qiangxi/ImageLib/blob/master/Image/GIF.gif?raw=true)  
这个插件的功能从上图可以一览无遗，大家如果想使用这个插件，可以点击[这里](http://download.csdn.net/download/qiang_xi/9950136)进行下载【唯一下载途径】，由于现在这个插件刚开发出来，功能还不强大并且可能存在一些 bug，所以并没有上传到 intellij 的插件仓库中。

### 总结

双向绑定的知识点也到此结束，总的来说，使用双向绑定的场景并不多，但是只要用到，那将会大大提高我们的开发效率。

到这一篇为止，个人规划的所有 DataBinding 的重要知识点都已讲解完毕，后面还会有一篇【总结 + 查漏补缺】的文章，有一些没讲到但又非常有用的知识点都会放到那一篇文章中去。

虽然我已尽最大努力试图把 DataBinding 的相关知识以及原理讲解清楚，但因个人能力及精力有限，文中难免有讲解不到位的地方，还请各位读者谅解；也难免存在疏漏或理解错误的地方，恳请各位开发者拍砖！！！