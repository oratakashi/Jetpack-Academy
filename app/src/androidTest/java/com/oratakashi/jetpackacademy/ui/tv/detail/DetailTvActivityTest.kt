package com.oratakashi.jetpackacademy.ui.tv.detail

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.main.MainActivity
import com.oratakashi.jetpackacademy.ui.tv.TvState
import com.oratakashi.jetpackacademy.ui.tv.TvViewModel
import com.oratakashi.jetpackacademy.utils.Converter
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.*
import javax.inject.Inject

@HiltAndroidTest
class DetailTvActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Suppress("DEPRECATION")
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Inject lateinit var repository: Repository

    lateinit var viewModel: TvViewModel

    @Before
    fun setUp(){
        onView(withId(R.id.vpMain))
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
    fun getDetail(){
        Thread.sleep(3000)
        when(val state = viewModel.state.value){
            is TvState.Result   -> {
                Assert.assertNotNull(state.data.data)
                val data : DataTv = state.data.data?.get(0)!!
                onView(withId(R.id.rvTv)).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                        ViewActions.click()
                    )
                )
                Thread.sleep(3000)

                onView(withId(R.id.tvTitle))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                onView(withId(R.id.tvTitle))
                    .check(ViewAssertions.matches(ViewMatchers.withText(data.name)))
                onView(withId(R.id.tvDescription))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                onView(withId(R.id.tvDescription))
                    .check(ViewAssertions.matches(ViewMatchers.withText(data.overview)))
                onView(withId(R.id.tvReleaseDate))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                onView(withId(R.id.tvReleaseDate))
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
            }
            else -> {
                throw UnknownError()
            }
        }
    }
}