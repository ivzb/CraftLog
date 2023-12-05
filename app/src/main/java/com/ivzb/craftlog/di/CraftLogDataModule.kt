package com.ivzb.craftlog.di

import android.app.Application
import androidx.room.Room
import com.ivzb.craftlog.data.CraftLogDatabase
import com.ivzb.craftlog.domain.repository.ExpenseRepository
import com.ivzb.craftlog.data.repository.ExpenseRepositoryImpl
import com.ivzb.craftlog.data.repository.InvestmentRepositoryImpl
import com.ivzb.craftlog.domain.repository.InvestmentRepository
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
    fun provideExpensesRepository(
        db: CraftLogDatabase
    ): ExpenseRepository {
        return ExpenseRepositoryImpl(
            dao = db.expensesDao
        )
    }

    @Provides
    @Singleton
    fun provideInvestmentsRepository(
        db: CraftLogDatabase
    ): InvestmentRepository {
        return InvestmentRepositoryImpl(
            dao = db.investmentsDao
        )
    }
}
