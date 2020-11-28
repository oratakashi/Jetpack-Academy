package com.oratakashi.jetpackacademy.data.repository.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oratakashi.jetpackacademy.data.factory.Factory
import com.oratakashi.jetpackacademy.data.factory.movie.MovieDataFactory
import com.oratakashi.jetpackacademy.data.factory.movie.MovieSearchDataFactory
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.tv.TvState
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import io.reactivex.disposables.CompositeDisposable
import java.lang.UnsupportedOperationException
import javax.inject.Inject
import javax.inject.Singleton


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

    override fun getMovie(callback: MutableLiveData<MovieState>) {
        endpoint.getMovie()
            .map<MovieState>(MovieState::Result)
            .onErrorReturn(MovieState::Error)
            .toFlowable()
            .startWith(MovieState.Loading).also {
                EspressoIdlingResource.increment()
            }
            .subscribe(callback::postValue).also {
                EspressoIdlingResource.decrement()
            }
            .let { return@let disposable::add }
    }

    override fun getMovieLimit(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        LivePagedListBuilder(
            factory.movieDataFactory.also {
                it.liveData = callback
            },
            config
        ).build().observeForever{
            data.postValue(it)
        }
    }

    override fun searchMovieLimit(
        query: String,
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        LivePagedListBuilder(
            factory.movieSearchDataFactory.also {
                it.liveData = callback
                it.keyword = query
            },
            config
        ).build().observeForever{
            data.postValue(it)
        }
    }

    override fun searchMovie(query: String, callback: MutableLiveData<MovieState>) {
        endpoint.searchMovie(query)
            .map<MovieState>(MovieState::Result)
            .onErrorReturn(MovieState::Error)
            .toFlowable()
            .startWith(MovieState.Loading).also {
                EspressoIdlingResource.increment()
            }
            .subscribe(callback::postValue).also {
                EspressoIdlingResource.decrement()
            }
            .let { return@let disposable::add }
    }

    override fun getTv(callback: MutableLiveData<TvState>) {
        endpoint.getTv()
            .map<TvState>(TvState::Result)
            .onErrorReturn(TvState::Error)
            .toFlowable()
            .startWith(TvState.Loading).also {
                EspressoIdlingResource.increment()
            }
            .subscribe(callback::postValue).also {
                EspressoIdlingResource.decrement()
            }
            .let { return@let disposable::add }
    }

    override fun searchTv(query: String, callback: MutableLiveData<TvState>) {
        endpoint.searchTv(query)
            .map<TvState>(TvState::Result)
            .onErrorReturn(TvState::Error)
            .toFlowable()
            .startWith(TvState.Loading).also {
                EspressoIdlingResource.increment()
            }
            .subscribe(callback::postValue).also {
                EspressoIdlingResource.decrement()
            }
            .let { return@let disposable::add }
    }
}