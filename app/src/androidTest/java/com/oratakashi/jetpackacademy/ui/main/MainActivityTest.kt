package com.oratakashi.jetpackacademy.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Suppress("DEPRECATION")
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun checkTabDisplayed() {
        onView(allOf(withId(R.id.bnMenu), isDisplayed()))
            .perform(click())
    }

    @Test
    fun swipePage() {
        onView(withId(R.id.vpMain))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeLeft())
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeLeft())
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeDown())
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeRight())
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeDown())
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeRight())
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeDown())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }
}