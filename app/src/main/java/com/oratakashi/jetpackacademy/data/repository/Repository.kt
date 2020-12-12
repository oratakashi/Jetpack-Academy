package com.oratakashi.jetpackacademy.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.oratakashi.jetpackacademy.data.database.Storage
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.tv.TvState
import io.reactivex.disposables.CompositeDisposable

interface Repository {
    fun getMovieLimit(callback : MutableLiveData<MovieState>, data : MutableLiveData<PagedList<DataMovie>>)
    fun getFavMovie(data : MutableLiveData<PagedList<DataMovie>>)
    fun searchMovieLimit(
        query: String,
        callback : MutableLiveData<MovieState>,
        data : MutableLiveData<PagedList<DataMovie>>
    )
    fun searchFavMovie(
        query: String,
        data : MutableLiveData<PagedList<DataMovie>>
    )

    fun getTv(callback: MutableLiveData<TvState>, data : MutableLiveData<PagedList<DataTv>>)
    fun getFavTv(data : MutableLiveData<PagedList<DataTv>>)
    fun searchFavTv(query: String, data : MutableLiveData<PagedList<DataTv>>)
    fun searchTvLimit(
        query: String,
        callback: MutableLiveData<TvState>,
        data : MutableLiveData<PagedList<DataTv>>
    )

    fun addData(data : DataMovie)
    fun checkData(data: DataMovie) : List<DataMovie>
    fun deleteData(data : DataMovie)
    fun addData(data : DataTv)
    fun checkData(data : DataTv) : List<DataTv>
    fun deleteData(data : DataTv)

    fun getDisposible() : CompositeDisposable
    fun getDatabase() : Storage
}