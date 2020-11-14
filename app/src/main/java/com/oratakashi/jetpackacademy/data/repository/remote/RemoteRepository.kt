package com.oratakashi.jetpackacademy.data.repository.remote

import androidx.lifecycle.MutableLiveData
import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.tv.TvState
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton


class RemoteRepository @Inject constructor(
    private val endpoint: ApiEndpoint
) : Repository{
    var disposable: CompositeDisposable = CompositeDisposable()

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