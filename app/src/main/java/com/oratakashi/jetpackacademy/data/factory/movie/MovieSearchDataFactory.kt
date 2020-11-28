package com.oratakashi.jetpackacademy.data.factory.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.source.movie.MovieDataSource
import com.oratakashi.jetpackacademy.data.source.movie.MovieSearchDataSource
import com.oratakashi.jetpackacademy.ui.movie.MovieState
import javax.inject.Inject

class MovieSearchDataFactory @Inject constructor(
    private val movieSearchDataSource: MovieSearchDataSource
) : DataSource.Factory<Int, DataMovie>(){

    lateinit var keyword: String
    lateinit var liveData: MutableLiveData<MovieState>

    override fun create(): DataSource<Int, DataMovie> {
        return movieSearchDataSource.also {
            it.keyword = keyword
            it.liveData = liveData
        }
    }
}