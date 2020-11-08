package com.oratakashi.jetpackacademy.ui.tv

import com.oratakashi.jetpackacademy.data.DataTv
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TvViewModelTest {

    lateinit var viewModel: TvViewModel

    @Before
    fun setup() {
        viewModel = TvViewModel()
    }

    @Test
    fun testGetData() {
        val data: List<DataTv> = viewModel.getData()
        Assert.assertNotNull(data)
        Assert.assertEquals(10, data.size)
    }
}