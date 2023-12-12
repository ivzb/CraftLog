package com.ivzb.craftlog.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ivzb.craftlog.data.dao.BudgetDao
import com.ivzb.craftlog.data.dao.ExpenseDao
import com.ivzb.craftlog.data.dao.InvestmentDao
import com.ivzb.craftlog.data.entity.BudgetEntity
import com.ivzb.craftlog.data.entity.ExpenseEntity
import com.ivzb.craftlog.data.entity.InvestmentEntity

@Database(
    entities = [
        ExpenseEntity::class,
        InvestmentEntity::class,
        BudgetEntity::class,
   ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class CraftLogDatabase : RoomDatabase() {

    abstract val expenseDao: ExpenseDao

    abstract val investmentDao: InvestmentDao

    abstract val budgetDao: BudgetDao

}
