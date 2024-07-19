package ru.easycode.zerotoheroandroidtdd

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyTask002 {

    @get:Rule
    val rule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun test_add_id() {
        Espresso.onView(
            ViewMatchers.withId(R.id.titleTextView)
        ).check(
            ViewAssertions.matches(
                ViewMatchers.withText("I am an Android Developer!")
            )
        )
    }
}