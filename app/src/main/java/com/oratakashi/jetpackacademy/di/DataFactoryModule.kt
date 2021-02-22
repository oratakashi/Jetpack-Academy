package com.oratakashi.jetpackacademy.di

import com.oratakashi.jetpackacademy.data.factory.Factory
import com.oratakashi.jetpackacademy.data.factory.movie.MovieDataFactory
import com.oratakashi.jetpackacademy.data.factory.movie.MovieSearchDataFactory
import com.oratakashi.jetpackacademy.data.factory.tv.TvDataFactory
import com.oratakashi.jetpackacademy.data.factory.tv.TvSearchDataFactory
import com.oratakashi.jetpackacademy.data.source.movie.MovieDataSource
import com.oratakashi.jetpackacademy.data.source.movie.MovieSearchDataSource
import com.oratakashi.jetpackacademy.data.source.tv.TvDataSource
import com.oratakashi.jetpackacademy.data.source.tv.TvSearchDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataFactoryModule {

    @Provides
    @Singleton
    fun provideFactory(
        movieDataFactory: MovieDataFactory,
        movieSearchDataFactory: MovieSearchDataFactory,
        tvDataFactory: TvDataFactory,
        tvSearchDataFactory: TvSearchDataFactory
    ) : Factory = Factory(
        movieDataFactory,
        movieSearchDataFactory,
        tvDataFactory,
        tvSearchDataFactory
    )

    @Provides
    @Singleton
    fun provideMovieFactory(
        movieDataSource: MovieDataSource
    ) : MovieDataFactory = MovieDataFactory(movieDataSource)

    @Provides
    @Singleton
    fun provideMovieSearchFactory(
        movieSearchDataSource: MovieSearchDataSource
    ) : MovieSearchDataFactory = MovieSearchDataFactory(movieSearchDataSource)

    @Provides
    @Singleton
    fun provideTvFactory(
        tvDataSource: TvDataSource
    ) : TvDataFactory = TvDataFactory(tvDataSource)

    @Provides
    @Singleton
    fun provideTvSearchFactory(
        tvSearchDataSource: TvSearchDataSource
    ) : TvSearchDataFactory = TvSearchDataFactory(tvSearchDataSource)
}