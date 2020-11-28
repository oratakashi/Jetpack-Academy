package com.oratakashi.jetpackacademy.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.tv.TvState
import io.reactivex.disposables.CompositeDisposable

interface Repository {
    fun getMovie(callback : MutableLiveData<MovieState>)
    fun getMovieLimit(callback : MutableLiveData<MovieState>, data : MutableLiveData<PagedList<DataMovie>>)
    fun searchMovie(query: String, callback : MutableLiveData<MovieState>)
    fun searchMovieLimit(
        query: String,
        callback : MutableLiveData<MovieState>,
        data : MutableLiveData<PagedList<DataMovie>>
    )

    fun getTv(callback: MutableLiveData<TvState>)
    fun searchTv(query: String, callback: MutableLiveData<TvState>)

    fun getDisposible() : CompositeDisposable
}