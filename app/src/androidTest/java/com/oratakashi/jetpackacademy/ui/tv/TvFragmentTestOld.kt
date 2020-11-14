package com.oratakashi.jetpackacademy.ui.tv

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.ui.main.MainActivity
import com.oratakashi.jetpackacademy.utils.RecyclerViewItemCountAssertion
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TvFragmentTestOld {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.launchActivity(Intent())
        onView(withId(R.id.vpMain))
            .perform(swipeLeft())
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.rvTv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rvTv))
            .check(RecyclerViewItemCountAssertion(10))
    }
}