package com.oratakashi.jetpackacademy.ui.movie

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.ui.main.MainActivity
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import com.oratakashi.jetpackacademy.utils.RecyclerViewItemCountAssertion
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovieFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Suppress("DEPRECATION")
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        EspressoIdlingResource.isMovie = true
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        Espresso.onView(allOf(isDisplayed(), withId(R.id.rvMovie)))
        Espresso.onView(allOf(isDisplayed(), withId(R.id.rvMovie)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        Espresso.onView(allOf(isDisplayed(), withId(R.id.rvMovie)))
            .check(RecyclerViewItemCountAssertion(20))
    }
}