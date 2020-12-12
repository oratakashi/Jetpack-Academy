package com.oratakashi.jetpackacademy.di

import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.data.repository.Repository
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
class DataSourceModule {
    @Provides
    @Singleton
    fun provideMovieDataSource(
        endpoint: ApiEndpoint
    ) : MovieDataSource = MovieDataSource(endpoint)

    @Provides
    @Singleton
    fun provideMovieSearchDataSource(
        endpoint: ApiEndpoint
    ) : MovieSearchDataSource = MovieSearchDataSource(endpoint)

    @Provides
    @Singleton
    fun provideTvDataSource(
        endpoint: ApiEndpoint
    ) : TvDataSource = TvDataSource(endpoint)

    @Provides
    @Singleton
    fun provideTvSearchDataSource(
        endpoint: ApiEndpoint
    ) : TvSearchDataSource = TvSearchDataSource(endpoint)
}