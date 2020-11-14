package com.oratakashi.jetpackacademy.ui.tv

import com.oratakashi.jetpackacademy.data.FakeRemoteRepository
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TvViewModelTest {

    lateinit var fakeRemoteRepository: FakeRemoteRepository
    lateinit var disposible : CompositeDisposable

    @Before
    fun setup() {
        fakeRemoteRepository = FakeRemoteRepository()
    }

    @Test
    fun testGetData() {
        val getData = fakeRemoteRepository.getTv()
            .blockingGet()

        Assert.assertNotNull(getData.data)
        Assert.assertEquals(20, getData.data?.size)
    }

    @Test
    fun testSearchData(){
        val getData = fakeRemoteRepository.searchTv("Love")
            .blockingGet()

        Assert.assertNotNull(getData.data)
        Assert.assertEquals(20, getData.data?.size)
    }
}