package com.oratakashi.jetpackacademy.ui.movie

import com.oratakashi.jetpackacademy.data.FakeRemoteRepository
import com.oratakashi.jetpackacademy.data.model.movie.ResponseMovie
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.data.repository.Repository
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.Answer
import javax.inject.Inject

class MovieViewModelTest {
    lateinit var fakeRemoteRepository: FakeRemoteRepository

    @Before
    fun setUp() {
        fakeRemoteRepository = FakeRemoteRepository()
    }

    @Test
    fun testGetData() {
        val getData = fakeRemoteRepository.getMovie()
            .blockingGet()

        Assert.assertNotNull(getData.data)
        Assert.assertEquals(20, getData.data?.size)
    }

    @Test
    fun testSearchData(){
        val getData = fakeRemoteRepository.searchMovie("Spi")
            .blockingGet()

        Assert.assertNotNull(getData.data)
        Assert.assertEquals(20, getData.data?.size)
    }
}