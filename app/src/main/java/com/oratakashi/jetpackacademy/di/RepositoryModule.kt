package com.oratakashi.jetpackacademy.di

import com.oratakashi.jetpackacademy.data.network.ApiEndpoint
import com.oratakashi.jetpackacademy.data.repository.DataRepository
import com.oratakashi.jetpackacademy.data.repository.Repository
import com.oratakashi.jetpackacademy.data.repository.remote.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRemoteRepository(endpoint: ApiEndpoint) : RemoteRepository = RemoteRepository(endpoint)

    @Singleton
    @Provides
    fun provideDataRepository(
        remoteRepository: RemoteRepository
    ) : DataRepository = DataRepository(remoteRepository)
}