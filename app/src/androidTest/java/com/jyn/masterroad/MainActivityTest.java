package com.jyn.masterroad;

import android.content.ComponentName;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    /**
     * public ActivityTestRule<MainActivity> activityScenarioRule = new ActivityTestRule<>(MainActivity.class);
     * <p>
     * ActivityTestRule的升级版，可以在测试开始之前给定活动，
     * 可以通过getScenario()方法访问scenario接口。
     */
    @Rule
    public ActivityTestRule<MainActivity> activityScenarioRule
            = new ActivityTestRule<>(MainActivity.class);

    /**
     * 设置等待时间
     */

//    @BeforeClass
//    public static void beforeClass() {
//        IdlingPolicies.setMasterPolicyTimeout(10, TimeUnit.SECONDS);
//        IdlingPolicies.setIdlingResourceTimeout(10, TimeUnit.SECONDS);
//    }
    @Test
    public void flow_btn_click_test() {
        onView(withId(R.id.main_fab))   //创建一个ViewMatcher
                .perform(click());  //进行一个Action操作

        //toast最后显示在界面上的是一个AppCompatTextView
        onView(withClassName(endsWith("AppCompatTextView")))
                .inRoot(withDecorView(not(activityScenarioRule
                        .getActivity()
                        .getWindow()
                        .getDecorView())))
                .check(matches(isDisplayed()));

        /*
         * 跳转断言
         * https://blog.csdn.net/yjp19871013/article/details/71597469
         */
//        intended(hasComponent(new ComponentName(getApplicationContext(), MainActivity.class)));
    }

    /**
     * RecyclerView item点击测试
     */
    @Test
    public void recycler_view_click_test() {
        onView(withId(R.id.main_recycle))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(1, click()));
    }
}
