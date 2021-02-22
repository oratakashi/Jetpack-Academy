package com.oratakashi.jetpackacademy.ui.tv

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.ui.main.MainActivity
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import com.oratakashi.jetpackacademy.utils.RecyclerViewItemCountAssertion
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TvFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Suppress("DEPRECATION")
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        EspressoIdlingResource.isTv = true
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeLeft())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadTvShows() {
        onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.rvTv)))
        onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.rvTv)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.rvTv)))
            .check(RecyclerViewItemCountAssertion(20))
    }
}