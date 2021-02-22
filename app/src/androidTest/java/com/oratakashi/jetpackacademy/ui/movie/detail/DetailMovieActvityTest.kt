package com.oratakashi.jetpackacademy.ui.movie.detail

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.database.Storage
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.main.MainActivity
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.movie.MovieViewModel
import com.oratakashi.jetpackacademy.utils.Converter
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import com.oratakashi.jetpackacademy.utils.RecyclerViewItemCountAssertion
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.*
import org.junit.*
import javax.inject.Inject

@HiltAndroidTest
class DetailMovieActvityTest {
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
        EspressoIdlingResource.isMovie = true
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun getDetail() {
        val getData = endpoint.getMovie(1)
            .blockingGet()

        val dataMovie = getData.data!![0]
        onView(allOf(isDisplayed(), withId(R.id.shLoading)))
        onView(allOf(isDisplayed(), withId(R.id.rvMovie)))
        onView(allOf(withId(R.id.rvMovie), isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        onView(allOf(withId(R.id.rvMovie), isDisplayed()))
            .check(RecyclerViewItemCountAssertion(20))

        onView(allOf(withId(R.id.rvMovie), isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        EspressoIdlingResource.incrementTest()

        //Remove selected data on DB
        repository.deleteData(dataMovie)
        EspressoIdlingResource.decrementTest()

        //Wait 3 Second

        onView(withId(R.id.tvTitle))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle))
            .check(matches(withText(dataMovie.title)))
        onView(withId(R.id.tvDescription))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvDescription))
            .check(matches(withText(dataMovie.overview)))
        onView(withId(R.id.tvReleaseDate))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate))
            .check(
                matches(
                    withText(
                        Converter.dateFormat(
                            dataMovie.release_date!!,
                            "yyyy-mm-dd",
                            "dd MMMM yyyy"
                        )
                    )
                )
            )

        //Insert Data on DB
        EspressoIdlingResource.incrementTest()
        onView(withId(R.id.ivFav))
            .perform(ViewActions.click())
        EspressoIdlingResource.decrementTest()

        //Check if data inserted on DB
        val dataDb = storage.movie().getDataById(dataMovie.id)
        Assert.assertNotNull(dataDb)
//        Assert.assertTrue(dataDb.isNotEmpty())
    }
}