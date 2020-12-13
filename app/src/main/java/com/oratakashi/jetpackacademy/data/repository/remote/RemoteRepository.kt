package com.oratakashi.jetpackacademy.data.repository.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oratakashi.jetpackacademy.data.database.Storage
import com.oratakashi.jetpackacademy.data.factory.Factory
import com.oratakashi.jetpackacademy.data.factory.movie.MovieDataFactory
import com.oratakashi.jetpackacademy.data.factory.movie.MovieSearchDataFactory
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.tv.TvState
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.UnsupportedOperationException


class RemoteRepository @Inject constructor(
    private val endpoint: ApiEndpoint,
    private val config : PagedList.Config,
    private val factory: Factory
) : Repository{

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun getDisposible(): CompositeDisposable {
        return disposable
    }

    override fun getMovieLimit(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            LivePagedListBuilder(
                factory.movieDataFactory.also {
                    it.liveData = callback
                },
                config
            ).build().observeForever(data::postValue)
        }
    }

    override fun searchMovieLimit(
        query: String,
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            LivePagedListBuilder(
                factory.movieSearchDataFactory.also {
                    it.liveData = callback
                    it.keyword = query
                },
                config
            ).build().observeForever(data::postValue)
        }
    }

    override fun getTv(
        callback: MutableLiveData<TvState>,
        data: MutableLiveData<PagedList<DataTv>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            LivePagedListBuilder(
                factory.tvDataFactory.also {
                    it.liveData = callback
                },
                config
            ).build().observeForever(data::postValue)
        }
    }

    override fun searchTvLimit(
        query: String,
        callback: MutableLiveData<TvState>,
        data: MutableLiveData<PagedList<DataTv>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            LivePagedListBuilder(
                factory.tvSearchDataFactory.also {
                    it.keyword = query
                    it.liveData = callback
                },
                config
            ).build().observeForever(data::postValue)
        }
    }

    override fun getFavMovie(
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getFavTv(data: MutableLiveData<PagedList<DataTv>>) {
        throw UnsupportedOperationException()
    }

    override fun addData(data: DataMovie) {
        throw UnsupportedOperationException()
    }

    override fun checkData(data: DataMovie): List<DataMovie> {
        throw UnsupportedOperationException()
    }

    override fun deleteData(data: DataMovie) {
        throw UnsupportedOperationException()
    }

    override fun addData(data: DataTv) {
        throw UnsupportedOperationException()
    }

    override fun checkData(data: DataTv): List<DataTv> {
        throw UnsupportedOperationException()
    }

    override fun deleteData(data: DataTv) {
        throw UnsupportedOperationException()
    }

    override fun searchFavMovie(query: String, data: MutableLiveData<PagedList<DataMovie>>) {
        throw UnsupportedOperationException()
    }

    override fun searchFavTv(query: String, data: MutableLiveData<PagedList<DataTv>>) {
        throw UnsupportedOperationException()
    }

    override fun getDatabase(): Storage {
        throw UnsupportedOperationException()
    }
}