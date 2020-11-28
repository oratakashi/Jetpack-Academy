package com.oratakashi.jetpackacademy.di

import com.oratakashi.jetpackacademy.data.repository.DataRepository
import com.oratakashi.jetpackacademy.data.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class BindingModule {
    @Binds
    abstract fun bindRepository(
        dataRepository: DataRepository
    ) : Repository
}