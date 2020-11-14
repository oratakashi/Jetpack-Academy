package com.oratakashi.jetpackacademy.di

import android.app.AlertDialog
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class UtilsModule {
    @Provides
    @Singleton
    fun provideDialog(@ApplicationContext context: Context) : AlertDialog.Builder =
        AlertDialog.Builder(context)
        .setTitle("Warning!")
        .setPositiveButton("OK"){ dialog, _ ->
            dialog.dismiss()
        }
}