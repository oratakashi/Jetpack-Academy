package com.oratakashi.jetpackacademy.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.oratakashi.jetpackacademy.data.database.Storage
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.repository.local.LocalRepository
import com.oratakashi.jetpackacademy.data.repository.remote.RemoteRepository
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.tv.TvState
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : Repository {

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

    override fun getTv(
        callback: MutableLiveData<TvState>,
        data: MutableLiveData<PagedList<DataTv>>
    ) {
        remoteRepository.getTv(callback, data)
    }

    override fun searchTvLimit(
        query: String,
        callback: MutableLiveData<TvState>,
        data: MutableLiveData<PagedList<DataTv>>
    ) {
        remoteRepository.searchTvLimit(query, callback, data)
    }

    override fun addData(data: DataMovie) {
        localRepository.addData(data)
    }

    override fun checkData(data: DataMovie): List<DataMovie> {
        return localRepository.checkData(data)
    }

    override fun deleteData(data: DataMovie) {
        localRepository.deleteData(data)
    }

    override fun addData(data: DataTv) {
        localRepository.addData(data)
    }

    override fun checkData(data: DataTv): List<DataTv> {
        return localRepository.checkData(data)
    }

    override fun deleteData(data: DataTv) {
        localRepository.deleteData(data)
    }

    override fun getFavMovie(data: MutableLiveData<PagedList<DataMovie>>) {
        localRepository.getFavMovie(data)
    }

    override fun getFavTv(data: MutableLiveData<PagedList<DataTv>>) {
        localRepository.getFavTv(data)
    }

    override fun searchFavMovie(query: String, data: MutableLiveData<PagedList<DataMovie>>) {
        localRepository.searchFavMovie(query, data)
    }

    override fun searchFavTv(query: String, data: MutableLiveData<PagedList<DataTv>>) {
        localRepository.searchFavTv(query, data)
    }

    override fun getDisposible(): CompositeDisposable {
        return remoteRepository.getDisposible()
    }

    override fun getDatabase(): Storage {
        return localRepository.getDatabase()
    }
}