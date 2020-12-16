package com.oratakashi.jetpackacademy.ui.favorite.tv

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.database.Storage
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.main.MainActivity
import com.oratakashi.jetpackacademy.ui.tv.TvState
import com.oratakashi.jetpackacademy.ui.tv.TvViewModel
import com.oratakashi.jetpackacademy.utils.Converter
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import com.oratakashi.jetpackacademy.utils.RecyclerViewItemCountAssertion
import com.oratakashi.jetpackacademy.utils.RecyclerViewItemEmptyAssertion
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.*
import javax.inject.Inject

@HiltAndroidTest
class TvFavFragmentTest {
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
        Espresso.onView(ViewMatchers.withId(R.id.vpMain))
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
    fun testInsert() {
        val getData = endpoint.getTv(1)
            .blockingGet()

        val dataTv = getData.data!![0]
        Espresso.onView(Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.rvTv)))
        Espresso.onView(Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.rvTv)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        Espresso.onView(Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.rvTv)))
            .check(RecyclerViewItemCountAssertion(20))

        Espresso.onView(Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.rvTv))).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

//        EspressoIdlingResource.incrementTest()
        //Remove selected data on DB
        repository.deleteData(dataTv)
//        EspressoIdlingResource.decrementTest()

        Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText(dataTv.name)))
        Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
            .check(ViewAssertions.matches(ViewMatchers.withText(dataTv.overview)))
        Espresso.onView(ViewMatchers.withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tvReleaseDate))
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

        EspressoIdlingResource.incrementTest()
        //Insert Data on DB
        Espresso.onView(ViewMatchers.withId(R.id.ivFav))
            .perform(ViewActions.click())
        EspressoIdlingResource.decrementTest()


        val dataDb = storage.tv().getDataById(dataTv.id)
        Assert.assertNotNull(dataDb)
//        Assert.assertTrue(dataDb.isNotEmpty())
    }

    @Test
    fun testSelect() {
        Espresso.onView(ViewMatchers.withId(R.id.vpMain))
            .perform(ViewActions.swipeLeft())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.vpMain))
            .perform(ViewActions.swipeLeft())

        //Wait to Swipe into Favorite
        Thread.sleep(3000)

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.navigation_fav_tv),
                ViewMatchers.isDisplayed()
            )
        )
            .perform(ViewActions.click())
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.rvFavTv),
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.rvFavTv),
                ViewMatchers.isDisplayed()
            )
        )
            .check(RecyclerViewItemEmptyAssertion(1))
    }
}