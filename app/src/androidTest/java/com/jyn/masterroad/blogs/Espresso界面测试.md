参考：  
[官方文档：测试单个应用的界面](https://developer.android.com/training/testing/ui-testing/espresso-testing)  
[官方文档：Espresso](https://developer.android.com/training/testing/espresso)  


# 使用规范
1. 通过调用 `onView()` 方法或 `AdapterView` 控件的 `onData()` 方法，在 `Activity` 中找到要测试的界面组件（例如，应用中的登录按钮）。
2. 通过调用 `ViewInteraction.perform()` 或 `DataInteraction.perform()`方法并传入用户操作 （例如，点击登录按钮），
   模拟要在该界面组件上执行的特定用户交互。 如需对同一界面组件上的多项操作进行排序，请在方法参数中使用逗号分隔列表将它们链接起来。
3. 根据需要重复上述步骤，以模拟目标应用中跨多个 `Activity` 的用户流。
4. 执行这些用户交互后，使用 `ViewAssertions` 方法检查界面是否反映了预期的状态或行为。
```kotlin
    onView(withId(R.id.my_view))           // withId(R.id.my_view) is a **ViewMatcher**
        .perform(click())                  // click() is a **ViewAction**
        .check(matches(isDisplayed()))     // matches(isDisplayed()) is a **ViewAssertion**

```

# API组件
* **Espresso** - 用于与视图交互（通过 `onView()` 和 `onData()`）的入口点。此外，还公开不一定与任何视图相关联的 API，如 `pressBack()`。
* **ViewMatchers** - 实现 `Matcher<? super View>` 接口的对象的集合。您可以将其中一个或多个对象传递给 `onView()` 方法，以在当前视图层次结构中找到某个视图。
* **ViewActions** - 可以传递给 `ViewInteraction.perform()` 方法的 `ViewAction` 对象的集合，例如 `click()`。
* **ViewAssertions** - 可以通过 `ViewInteraction.check()` 方法传递的 `ViewAssertion` 对象的集合。在大多数情况下，您将使用 `matches` 断言，它使用视图匹配器断言当前选定视图的状态。

