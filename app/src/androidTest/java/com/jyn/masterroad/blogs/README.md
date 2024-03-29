## Espresso (测试单个应用的界面)

> Espresso 来编写简洁、美观且可靠的 Android 界面测试  
> [Espresso官方Demo](https://github.com/android/testing-samples/tree/master/ui/espresso/BasicSample)  
> [官方文档：测试单个应用的界面](https://developer.android.com/training/testing/ui-testing/espresso-testing)  
> [官方文档：Espresso](https://developer.android.com/training/testing/espresso)  
> [测试应用的 Activity](https://developer.android.com/guide/components/activities/testing)  
> [测试 Fragment](https://developer.android.com/guide/fragments/test)  

### Espresso 的主要组件包括：

* **Espresso** - 用于与视图交互（通过 onView() 和 onData()）的入口点。此外，还公开不一定与任何视图相关联的 API，如 pressBack()。
* **ViewMatchers(视图匹配器)** - 实现 Matcher<? super View> 接口的对象的集合。您可以将其中一个或多个对象传递给 onView() 方法，以在当前视图层次结构中找到某个视图。
* **ViewActions(视图操作器)** - 可以传递给 ViewInteraction.perform() 方法的 ViewAction 对象的集合，例如 click()。
* **ViewAssertions(断言：判断结果是否满足预期)** - 可以通过 ViewInteraction.check() 方法传递的 ViewAssertion 对象的集合。在大多数情况下，您将使用 matches 断言，它使用视图匹配器断言当前选定视图的状态。

```java
    // withId(R.id.my_view) is a ViewMatcher
    // click() is a ViewAction
    // matches(isDisplayed()) is a ViewAssertion
    onView(withId(R.id.my_view))
        .perform(click())
        .check(matches(isDisplayed()));
```

#### ViewMatchers

[ViewMatchers 官方Api文档](https://developer.android.com/reference/androidx/test/espresso/matcher/ViewMatchers)
可以使用组合器来缩小搜索范围

```java
    onView(allOf(withId(R.id.my_view), withText("Hello!")))
    onView(allOf(withId(R.id.my_view), not(withText("Unwanted"))))
```

#### ViewActions类

> 该类提供了指定常用操作的帮助方法列表。您可以使用这些方法作为方便的快捷方式，而不是创建和配置单个ViewAction对象。您可以指定以下操作：

* ViewActions.click()：点击视图。
* ViewActions.typeText()：点击视图并输入指定的字符串。
* ViewActions.scrollTo()：滚动到视图。目标视图必须是来自ScrollView的子类，它的android：visibility属性的值必须是VISIBLE。对于扩展AdapterView（例如，ListView）的视图，onData()方法为您处理滚动。
* ViewActions.pressKey()：使用指定的键码执行键按下。
* ViewActions.clearText()：清除目标视图中的文本。
  如果目标视图位于ScrollView内部，请先执行ViewActions.scrollTo()操作，以在其他操作进行之前在屏幕中显示视图。如果视图已显示，ViewActions.scrollTo()操作将不起作用。

## UiAutomator (测试多个应用的界面)

> UI Automator 测试框架提供了一组 API，用于构建在用户应用和系统应用上执行交互的界面测试。
> 通过 UI Automator API，您可以执行在测试设备中打开“设置”菜单或应用启动器等操作。
> [UiAutomator官方Demo](https://github.com/android/testing-samples/blob/master/ui/uiautomator/BasicSample)
> [官方文档：测试多个应用的界面](https://developer.android.com/training/testing/ui-testing/uiautomator-testing)
> [官方文档：UI Automator](https://developer.android.com/training/testing/ui-automator)

## mockito

[mockito 官网](https://github.com/mockito/mockito)

# 问题

### 错误: java.lang.AssertionError: Activity never becomes requested state "[RESUMED]" (last lifecycle transition = "PRE_ON_CREATE")

> Android 的仪器测试 (Instrumented Test) 都会构建一个独立的名字叫 `APP.test.apk`的APP 并自动安装和运行。
> 而国产的手机系统对于应用自启动的管理非常激进（例如华为），如果没有对 `APP.test.apk` 设置白名单，于是系统就一直禁止 `APP包名.test.apk` 的启动，导致测试失败。

**解决：** 在华为的手机应用管家中为 `APP.test.apk` 设置白名单，测试就可以通过了。

#博客
**单元测试之JUnit4** TODO
https://www.jianshu.com/p/e7147839c452

**[译]Android测试: 单元测试（Part 1）**
https://zhuanlan.zhihu.com/p/45203314

**[译]Android测试: Espresso（Part 3）** TODO
https://zhuanlan.zhihu.com/p/47121164

**[译]Android测试: UI 自动化测试（Part 4）**
https://zhuanlan.zhihu.com/p/47121864

**Android 自动化测试 Espresso篇：异步代码测试**
https://blog.csdn.net/mq2553299/article/details/74490718

**GitHook 工具 —— husky介绍及使用**
https://www.cnblogs.com/jiaoshou/p/12222665.html

**使用 git hook 规范 Android 项目**
https://www.jianshu.com/p/faef72852bf8

**Git-hook在Android-gradle中的简单使用**
https://tanghuaizhe.github.io/2019/07/06/Git-hook%E5%9C%A8Android-gradle%E4%B8%AD%E7%9A%84%E7%AE%80%E5%8D%95%E4%BD%BF%E7%94%A8.html

**espresso-api之Matchers探究**
https://qaseven.github.io/2016/12/25/espresso4/

**espresso系列一简介**
https://qaseven.github.io/2016/11/30/expresso/

**Android自动化测试**
https://www.cnblogs.com/by-dream/tag/Android%E8%87%AA%E5%8A%A8%E5%8C%96%E6%B5%8B%E8%AF%95/

**AndroidX 测试坑点详解（一）**
https://wafer.li/Android/android-%E6%B5%8B%E8%AF%95%E5%9D%91%E7%82%B9%E8%AF%A6%E8%A7%A3%EF%BC%88%E4%B8%80%EF%BC%89/
Fragment
https://www.5axxw.com/questions/content/k6poiz

Android Espresso：如何在测试失败时添加自己的日志输出？
http://www.voidcn.com/article/p-hinchjer-bwh.html

测试
https://www.jianshu.com/u/363840decb4c

Android自动化测试框架Espresso（三）——View Action的使用
https://blog.csdn.net/yjp19871013/article/details/71123464
Android自动化测试框架Espresso（四）——intent测试
https://blog.csdn.net/yjp19871013/article/details/71597469
@PrepareForTest的使用场景
https://blog.csdn.net/qaz296490709/article/details/72880370
