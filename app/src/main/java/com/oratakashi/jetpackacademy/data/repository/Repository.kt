package com.oratakashi.jetpackacademy.data.repository

import androidx.lifecycle.MutableLiveData
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.tv.TvState
import io.reactivex.disposables.CompositeDisposable

interface Repository {
    fun getMovie(callback : MutableLiveData<MovieState>)
    fun searchMovie(query: String, callback : MutableLiveData<MovieState>)

    fun getTv(callback: MutableLiveData<TvState>)
    fun searchTv(query: String, callback: MutableLiveData<TvState>)

    fun getDisposible() : CompositeDisposable
}