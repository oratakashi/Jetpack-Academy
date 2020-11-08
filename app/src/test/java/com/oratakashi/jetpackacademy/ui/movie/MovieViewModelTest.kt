package com.oratakashi.jetpackacademy.ui.movie

import com.oratakashi.jetpackacademy.data.DataMovie
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieViewModelTest {

    lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        viewModel = MovieViewModel()
    }

    @Test
    fun testGetData() {
        val data: List<DataMovie> = viewModel.getData()
        Assert.assertNotNull(data)
        Assert.assertEquals(10, data.size)
    }
}