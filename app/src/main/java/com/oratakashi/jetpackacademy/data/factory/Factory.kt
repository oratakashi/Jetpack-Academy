package com.oratakashi.jetpackacademy.data.factory

import com.oratakashi.jetpackacademy.data.factory.movie.MovieDataFactory
import com.oratakashi.jetpackacademy.data.factory.movie.MovieSearchDataFactory
import com.oratakashi.jetpackacademy.data.factory.tv.TvDataFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class Factory  @Inject constructor(
    val movieDataFactory: MovieDataFactory,
    val movieSearchDataFactory: MovieSearchDataFactory,
    val tvDataFactory: TvDataFactory
)