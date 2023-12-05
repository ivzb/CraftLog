package com.ivzb.craftlog.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ivzb.craftlog.data.entity.ExpenseEntity
import com.ivzb.craftlog.data.entity.InvestmentEntity

@Database(
    entities = [
        ExpenseEntity::class,
        InvestmentEntity::class,
   ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class CraftLogDatabase : RoomDatabase() {

    abstract val expensesDao: ExpenseDao

    abstract val investmentsDao: InvestmentDao

}
