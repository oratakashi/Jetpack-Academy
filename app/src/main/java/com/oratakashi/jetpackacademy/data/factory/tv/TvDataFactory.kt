package com.oratakashi.jetpackacademy.data.factory.tv

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.source.tv.TvDataSource
import com.oratakashi.jetpackacademy.ui.tv.TvState
import javax.inject.Inject

class TvDataFactory @Inject constructor(
    private val tvDataSource: TvDataSource
) : DataSource.Factory<Int, DataTv>() {

    lateinit var liveData: MutableLiveData<TvState>

    override fun create(): DataSource<Int, DataTv> {
        return tvDataSource.also {
            it.liveData = liveData
        }
    }
}