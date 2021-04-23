package com.jyn.masterroad

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.truth.content.IntentSubject.assertThat
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
 * https://stackoverflow.com/questions/54179560/how-to-putextra-data-using-newest-activityscenariorule-activityscenarioespress
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTestKt {
    private val intent = Intent(getApplicationContext(), MainActivity::class.java).apply {
        putExtra("MyIntentParameterKey", "MyIntentParameterValue")
    }

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(intent)

    @Test
    fun myTest() {
        onView(withId(R.id.main_fab)) //创建一个ViewMatcher
                .perform(ViewActions.click())
    }

    @Test
    fun test() {
        launchActivity<MainActivity>(intent).use { scenario ->
            scenario.onActivity { activity ->
                assertThat(activity.intent)
                        .extras()
                        .string("MyIntentParameterKey")
                        .isEqualTo("MyIntentParameterValue")
            }
        }
    }

    @After
    fun tearDown() {
        activityScenarioRule.scenario.close()
    }
}