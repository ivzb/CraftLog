package com.ivzb.craftlog.di

import android.content.Context
import com.ivzb.craftlog.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(): Context {
        return App.applicationContext()
    }

}
