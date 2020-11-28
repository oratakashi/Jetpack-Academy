package com.oratakashi.jetpackacademy.data.factory.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.source.movie.MovieDataSource
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import javax.inject.Inject

class MovieDataFactory @Inject constructor(
    private val movieDataSource: MovieDataSource
) : DataSource.Factory<Int, DataMovie>(){

    lateinit var liveData: MutableLiveData<MovieState>

    override fun create(): DataSource<Int, DataMovie> {
        return movieDataSource.also {
            it.liveData = liveData
        }
    }
}