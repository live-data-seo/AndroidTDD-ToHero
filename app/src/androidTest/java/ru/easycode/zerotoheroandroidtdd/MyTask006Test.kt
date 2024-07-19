package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyTask006Test {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_change_text_runtime() {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withText("Hello World!"),
                withId(R.id.titleTextView),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(
            matches(isDisplayed())
        )

        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withId(R.id.changeButton),
                withText("change"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(
            isCompletelyBelow(withId(R.id.titleTextView))
        )

        onView(withId(R.id.changeButton)).perform(click())
        onView(withId(R.id.titleTextView)).check(
            matches(withText("I am an Android Developer!"))
        )
    }
}