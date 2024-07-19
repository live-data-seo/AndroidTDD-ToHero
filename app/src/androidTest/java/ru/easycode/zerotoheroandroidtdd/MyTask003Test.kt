package ru.easycode.zerotoheroandroidtdd

import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyTask003Test {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_change_parent(){
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.titleTextView),
                ViewMatchers.withText("I am an Android Developer!"),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(LinearLayout::class.java))
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }
}