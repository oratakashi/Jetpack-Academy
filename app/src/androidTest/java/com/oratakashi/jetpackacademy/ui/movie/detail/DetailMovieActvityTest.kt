package com.oratakashi.jetpackacademy.ui.movie.detail

import android.content.Intent
import androidx.fragment.app.Fragment
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
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.main.MainActivity
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.movie.MovieViewModel
import com.oratakashi.jetpackacademy.utils.Converter
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.*
import javax.inject.Inject

@HiltAndroidTest
class DetailMovieActvityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Suppress("DEPRECATION")
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Inject lateinit var repository: Repository

    lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = MovieViewModel(repository)
        viewModel.getMovie()
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
            is MovieState.Result    -> {
                Assert.assertNotNull(state.data.data)
                val data : DataMovie = state.data.data?.get(0)!!
                onView(withId(R.id.rvMovie)).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                        ViewActions.click()
                    ))

                //Wait 3 Second
                Thread.sleep(3000)

                onView(withId(R.id.tvTitle))
                    .check(matches(isDisplayed()))
                onView(withId(R.id.tvTitle))
                    .check(matches(withText(data.title)))
                onView(withId(R.id.tvDescription))
                    .check(matches(isDisplayed()))
                onView(withId(R.id.tvDescription))
                    .check(matches(withText(data.overview)))
                onView(withId(R.id.tvReleaseDate))
                    .check(matches(isDisplayed()))
                onView(withId(R.id.tvReleaseDate))
                    .check(matches(withText(
                        Converter.dateFormat(
                            data.release_date!!,
                            "yyyy-mm-dd",
                            "dd MMMM yyyy"
                        )
                    )))
            }
            else -> {
                throw UnknownError()
            }
        }
    }
}