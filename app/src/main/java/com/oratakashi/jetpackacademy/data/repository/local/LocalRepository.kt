package com.oratakashi.jetpackacademy.data.repository.local

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oratakashi.jetpackacademy.data.database.Storage
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import com.oratakashi.jetpackacademy.ui.tv.TvState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val storage: Storage,
    private val config: PagedList.Config
) : Repository{

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun addData(data: DataMovie) {
        storage.movie().add(data)
    }

    override fun checkData(data: DataMovie): List<DataMovie> {
        return storage.movie().getDataById(data.id)
    }

    override fun deleteData(data: DataMovie) {
        storage.movie().delete(data)
    }

    override fun addData(data: DataTv) {
        storage.tv().add(data)
    }

    override fun checkData(data: DataTv): List<DataTv> {
        return storage.tv().getDataById(data.id)
    }

    override fun deleteData(data: DataTv) {
        storage.tv().delete(data)
    }

    override fun getFavMovie(
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        LivePagedListBuilder(
            storage.movie().getData(),
            config
        ).build().observeForever(data::postValue)
    }

    override fun getFavTv(data: MutableLiveData<PagedList<DataTv>>) {
        LivePagedListBuilder(
            storage.tv().getData(),
            config
        ).build().observeForever(data::postValue)
    }

    override fun searchFavMovie(query: String, data: MutableLiveData<PagedList<DataMovie>>) {
        LivePagedListBuilder(
            storage.movie().searchData("%$query%"),
            config
        ).build().observeForever(data::postValue)
    }

    override fun searchFavTv(query: String, data: MutableLiveData<PagedList<DataTv>>) {
        LivePagedListBuilder(
            storage.tv().searchData("%$query%"),
            config
        ).build().observeForever(data::postValue)
    }

    override fun getMovieLimit(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        throw UnsupportedOperationException()
    }

    override fun searchMovieLimit(
        query: String,
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getTv(
        callback: MutableLiveData<TvState>,
        data: MutableLiveData<PagedList<DataTv>>
    ) {
        throw UnsupportedOperationException()
    }


    override fun searchTvLimit(
        query: String,
        callback: MutableLiveData<TvState>,
        data: MutableLiveData<PagedList<DataTv>>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getDisposible(): CompositeDisposable {
        return disposable
    }

    override fun getDatabase(): Storage {
        return storage
    }
}