package com.oratakashi.jetpackacademy.ui.movie.detail

import android.content.Context
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.DataMovie
import com.oratakashi.jetpackacademy.utils.EspressoTestsMatchers
import com.oratakashi.jetpackacademy.utils.FakeData
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailMovieActivityTest {

    private val dataDummy: DataMovie = DataMovie(
        FakeData.movieData[0][0],
        FakeData.movieData[0][1],
        FakeData.movieData[0][2],
        FakeData.movieData[0][3],
        FakeData.movieData[0][4],
        FakeData.movieData[0][5],
        FakeData.movieData[0][6]
    )
    lateinit var context: Context

    @get:Rule
    val activityRule: ActivityTestRule<DetailMovieActivity> =
        object : ActivityTestRule<DetailMovieActivity>(DetailMovieActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext =
                    InstrumentationRegistry.getInstrumentation()
                        .targetContext
                val result = Intent(targetContext, DetailMovieActivity::class.java)
                result.putExtra(
                    "data", DataMovie(
                        FakeData.movieData[0][0],
                        FakeData.movieData[0][1],
                        FakeData.movieData[0][2],
                        FakeData.movieData[0][3],
                        FakeData.movieData[0][4],
                        FakeData.movieData[0][5],
                        FakeData.movieData[0][6]
                    )
                )
                return result
            }
        }

    @Before
    fun setUp() {
        context = activityRule.activity.applicationContext
    }

    @Test
    fun loadDetails() {
        onView(withId(R.id.tvTitle))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle))
            .check(matches(withText(dataDummy.title)))
        onView(withId(R.id.tvDescription))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvDescription))
            .check(matches(withText(dataDummy.description)))
        onView(withId(R.id.tvDuration))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvDuration))
            .check(matches(withText(dataDummy.duration)))
        onView(withId(R.id.tvLanguage))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvLanguage))
            .check(matches(withText(dataDummy.language)))
        onView(withId(R.id.tvReleaseDate))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate))
            .check(matches(withText(dataDummy.date)))
        onView(withId(R.id.ivPhoto))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ivPhoto))
            .check(
                matches(
                    EspressoTestsMatchers().withDrawable(
                        context.resources.getIdentifier(
                            dataDummy.img,
                            "drawable",
                            context.packageName
                        )
                    )
                )
            )
    }
}