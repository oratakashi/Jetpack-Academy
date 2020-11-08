package com.oratakashi.jetpackacademy.ui.tv.detail

import android.content.Context
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.DataTv
import com.oratakashi.jetpackacademy.utils.EspressoTestsMatchers
import com.oratakashi.jetpackacademy.utils.FakeData
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailTvActivityTest {
    private val dataDummy: DataTv = DataTv(
        FakeData.tvShowData[0][0],
        FakeData.tvShowData[0][1],
        FakeData.tvShowData[0][2],
        FakeData.tvShowData[0][3],
        FakeData.tvShowData[0][4],
        FakeData.tvShowData[0][5],
        FakeData.tvShowData[0][6]
    )
    lateinit var context: Context

    @get:Rule
    val activityRule: ActivityTestRule<DetailTvActivity> =
        object : ActivityTestRule<DetailTvActivity>(
            DetailTvActivity::class.java
        ) {
            override fun getActivityIntent(): Intent {
                val targetContext =
                    InstrumentationRegistry.getInstrumentation()
                        .targetContext
                val result = Intent(targetContext, DetailTvActivity::class.java)
                result.putExtra(
                    "data", DataTv(
                        FakeData.tvShowData[0][0],
                        FakeData.tvShowData[0][1],
                        FakeData.tvShowData[0][2],
                        FakeData.tvShowData[0][3],
                        FakeData.tvShowData[0][4],
                        FakeData.tvShowData[0][5],
                        FakeData.tvShowData[0][6]
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
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText(dataDummy.title)))
        Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
            .check(ViewAssertions.matches(ViewMatchers.withText(dataDummy.description)))
        Espresso.onView(ViewMatchers.withId(R.id.tvDuration))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tvDuration))
            .check(ViewAssertions.matches(ViewMatchers.withText(dataDummy.duration)))
        Espresso.onView(ViewMatchers.withId(R.id.tvLanguage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tvLanguage))
            .check(ViewAssertions.matches(ViewMatchers.withText(dataDummy.language)))
        Espresso.onView(ViewMatchers.withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(ViewMatchers.withText(dataDummy.date)))
        Espresso.onView(ViewMatchers.withId(R.id.ivPhoto))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.ivPhoto))
            .check(
                ViewAssertions.matches(
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