package com.oratakashi.jetpackacademy.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.repository.remote.RemoteRepository
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.tv.TvState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteRepository: RemoteRepository
) : Repository {
    override fun getMovie(callback: MutableLiveData<MovieState>) {
        remoteRepository.getMovie(callback)
    }

    override fun searchMovie(query: String, callback: MutableLiveData<MovieState>) {
        remoteRepository.searchMovie(query, callback)
    }

    override fun getMovieLimit(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        remoteRepository.getMovieLimit(callback, data)
    }

    override fun searchMovieLimit(
        query: String,
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        remoteRepository.searchMovieLimit(query, callback, data)
    }

    override fun getTv(callback: MutableLiveData<TvState>) {
        remoteRepository.getTv(callback)
    }

    override fun searchTv(query: String, callback: MutableLiveData<TvState>) {
        remoteRepository.searchTv(query, callback)
    }

    override fun getDisposible(): CompositeDisposable {
        return remoteRepository.getDisposible()
    }
}