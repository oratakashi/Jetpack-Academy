package com.oratakashi.jetpackacademy.data.factory.tv

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.source.tv.TvSearchDataSource
import com.oratakashi.jetpackacademy.ui.tv.TvState
import javax.inject.Inject

class TvSearchDataFactory @Inject constructor(
    private val tvSearchDataSource: TvSearchDataSource
) : DataSource.Factory<Int, DataTv>(){

    lateinit var liveData: MutableLiveData<TvState>
    lateinit var keyword: String

    override fun create(): DataSource<Int, DataTv> {
        return tvSearchDataSource.also {
            it.keyword = keyword
            it.liveData = liveData
        }
    }
}