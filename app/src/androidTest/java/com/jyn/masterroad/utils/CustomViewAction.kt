package com.jyn.masterroad.utils

import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

/*
 * TextView设置文本
 */
fun tvSetText(value: String): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = allOf(isDisplayed(), isAssignableFrom(TextView::class.java))

        override fun getDescription(): String = "replace text"

        override fun perform(uiController: UiController?, view: View?) {
            (view as TextView).text = value
        }
    }
}