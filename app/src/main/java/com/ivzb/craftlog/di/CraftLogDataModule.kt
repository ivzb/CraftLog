package com.waseefakhtar.doseapp.di

import com.ivzb.craftlog.domain.repository.ExpensesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CraftLogDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideCraftLogDatabase(app: Application): CraftLogDatabase {
        return Room.databaseBuilder(
            app,
            CraftLogDatabase::class.java,
            "craft_log_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCraftLogRepository(
        db: CraftLogDatabase
    ): ExpensesRepository {
        return ExpensesRepositoryImpl(
            dao = db.dao
        )
    }
}
