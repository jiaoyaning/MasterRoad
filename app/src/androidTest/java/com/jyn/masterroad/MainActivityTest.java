package com.jyn.masterroad;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by jiaoyaning on 2021/4/17.
 */
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

//    @BeforeClass
//    public static void beforeClass() {
//        IdlingPolicies.setMasterPolicyTimeout(10, TimeUnit.SECONDS);
//        IdlingPolicies.setIdlingResourceTimeout(10, TimeUnit.SECONDS);
//    }

    @Test
    public void flow_btn_click_test() throws InterruptedException {
//        onView(withId(R.id.main_fab))   //创建一个ViewMatcher
//                .perform(click());  //进行一个Action操作

//        Thread.sleep(1000);

        onView(withText("被点击")).
                inRoot(withDecorView(not(activityScenarioRule
                        .getActivity()
                        .getWindow()
                        .getDecorView())))
                .check(matches(isDisplayed()));
    }
}
