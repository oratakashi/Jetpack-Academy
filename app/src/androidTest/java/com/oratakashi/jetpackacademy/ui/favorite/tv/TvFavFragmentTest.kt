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
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.main.MainActivity
import com.oratakashi.jetpackacademy.ui.tv.TvState
import com.oratakashi.jetpackacademy.ui.tv.TvViewModel
import com.oratakashi.jetpackacademy.utils.Converter
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
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

    lateinit var viewModel: TvViewModel

    @Before
    fun setUp(){
        Espresso.onView(ViewMatchers.withId(R.id.vpMain))
            .perform(ViewActions.swipeLeft())
        hiltRule.inject()

        viewModel = TvViewModel(repository)
        viewModel.getTv()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun testInsert(){
        Thread.sleep(3000)
        Espresso.onView(Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.rvTv)))
        Thread.sleep(3000)
        when(val state = viewModel.state.value){
            is TvState.Result   -> {
                Assert.assertNotNull(state.data.data)
                val data : DataTv = state.data.data?.get(0)!!
                Espresso.onView(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.withId(R.id.rvTv)
                    )
                ).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                        ViewActions.click()
                    )
                )

                //Remove selected data on DB
                repository.deleteData(data)

                //Wait 3 Second
                Thread.sleep(3000)

                Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
                    .check(ViewAssertions.matches(ViewMatchers.withText(data.name)))
                Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
                    .check(ViewAssertions.matches(ViewMatchers.withText(data.overview)))
                Espresso.onView(ViewMatchers.withId(R.id.tvReleaseDate))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                Espresso.onView(ViewMatchers.withId(R.id.tvReleaseDate))
                    .check(
                        ViewAssertions.matches(
                            ViewMatchers.withText(
                                Converter.dateFormat(
                                    data.first_air_date!!,
                                    "yyyy-mm-dd",
                                    "dd MMMM yyyy"
                                )
                            )
                        )
                    )

                //Insert Data on DB
                Espresso.onView(ViewMatchers.withId(R.id.ivFav))
                    .perform(ViewActions.click())

                Thread.sleep(3000)

                val dataTv = storage.tv().getDataById(data.id)
                Assert.assertNotNull(dataTv)
                Assert.assertTrue(dataTv.isNotEmpty())

                //Exit Activity
                Espresso.onView(ViewMatchers.withId(R.id.fab))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                Espresso.onView(ViewMatchers.withId(R.id.fab))
                    .perform(ViewActions.click())
            }
            is TvState.Loading  -> {

            }
            is TvState.Error    -> {
                throw Throwable(state.error.message)
            }
            else -> {
                throw UnknownError()
            }
        }
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