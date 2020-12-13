package com.oratakashi.jetpackacademy.data.source.tv

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.ui.tv.TvState
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TvSearchDataSource @Inject constructor(
    private val endpoint: ApiEndpoint
) : PageKeyedDataSource<Int, DataTv>(){

    lateinit var liveData: MutableLiveData<TvState>
    lateinit var keyword: String

    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DataTv>
    ) {
        endpoint.searchTv(keyword, 1)
            .map<TvState> {
                callback.onResult(it.data!!.toMutableList(), 1, 2)
                TvState.Result(it)
            }
            .onErrorReturn(TvState::Error)
            .toFlowable()
            .startWith(TvState.Loading).also {
                EspressoIdlingResource.increment()
            }
            .subscribe(liveData::postValue).also {
                EspressoIdlingResource.decrement()
            }
            .let { return@let disposable::add }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataTv>) {
        endpoint.searchTv(keyword, params.key)
            .map<TvState> {
                callback.onResult(it.data!!.toMutableList(), params.key + 1)
                TvState.Result(it)
            }
            .onErrorReturn(TvState::Error)
            .toFlowable()
            .subscribe(liveData::postValue)
            .let { return@let disposable::add }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataTv>) {

    }
}