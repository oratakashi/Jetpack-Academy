package com.oratakashi.jetpackacademy.ui.tv.detail

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.database.Storage
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.main.MainActivity
import com.oratakashi.jetpackacademy.ui.tv.TvViewModel
import com.oratakashi.jetpackacademy.utils.Converter
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import com.oratakashi.jetpackacademy.utils.RecyclerViewItemCountAssertion
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.*
import javax.inject.Inject

@HiltAndroidTest
class DetailTvActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Suppress("DEPRECATION")
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Inject
    lateinit var repository: Repository
    @Inject
    lateinit var storage: Storage
    @Inject
    lateinit var endpoint: ApiEndpoint

    @Before
    fun setUp() {
        onView(withId(R.id.vpMain))
            .perform(ViewActions.swipeLeft())
        EspressoIdlingResource.isTv = true
        hiltRule.inject()

        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun getDetail() {
        val getData = endpoint.getTv(1)
            .blockingGet()

        val dataTv = getData.data!![0]
        onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.rvTv)))
        onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.rvTv)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.rvTv)))
            .check(RecyclerViewItemCountAssertion(20))

        onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.rvTv))).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        EspressoIdlingResource.increment()
        //Remove selected data on DB
        repository.deleteData(dataTv)
        EspressoIdlingResource.decrement()

        onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText(dataTv.name)))
        onView(withId(R.id.tvDescription))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tvDescription))
            .check(ViewAssertions.matches(ViewMatchers.withText(dataTv.overview)))
        onView(withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tvReleaseDate))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.withText(
                        Converter.dateFormat(
                            dataTv.first_air_date!!,
                            "yyyy-mm-dd",
                            "dd MMMM yyyy"
                        )
                    )
                )
            )

        //Insert Data on DB
        onView(withId(R.id.ivFav))
            .perform(ViewActions.click())


        val dataDb = storage.tv().getDataById(dataTv.id)
        Assert.assertNotNull(dataDb)
        Assert.assertTrue(dataDb.isNotEmpty())
    }
}