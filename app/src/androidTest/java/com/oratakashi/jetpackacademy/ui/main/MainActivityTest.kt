package com.oratakashi.jetpackacademy.ui.main

import android.content.Intent
import androidx.annotation.UiThread
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {
    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule<MainActivity>(MainActivity::class.java)
    lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {
        mainActivity = activityRule.activity
        activityRule.launchActivity(Intent())
        assertThat(activityRule, notNullValue())
    }

    @Test
    fun checkTabDisplayed() {
        onView(withId(R.id.bnMenu))
            .perform(click())
            .check(matches(isDisplayed()))
    }

    @Test
    @UiThread
    fun checkSwitchTabs() {
        onView(allOf(withId(R.id.navigation_tv)))
            .perform(click())
            .check(matches(isDisplayed()))
    }

    @Test
    fun swipePage() {
        onView(withId(R.id.vpMain))
            .check(matches(isDisplayed()))
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeLeft())
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeDown())
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeRight())
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeDown())
    }
}