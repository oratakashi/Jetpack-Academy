package com.oratakashi.jetpackacademy.ui.movie

import com.oratakashi.jetpackacademy.data.FakeRemoteRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
    fun testSearchData() {
        val getData = fakeRemoteRepository.searchMovie("Spi")
            .blockingGet()

        Assert.assertNotNull(getData.data)
        Assert.assertEquals(20, getData.data?.size)
    }
}