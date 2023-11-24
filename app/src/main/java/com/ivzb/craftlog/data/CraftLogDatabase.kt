package com.ivzb.craftlog.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.waseefakhtar.doseapp.data.entity.ExpenseEntity

@Database(
    entities = [ExpenseEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class CraftLogDatabase : RoomDatabase() {

    abstract val dao: ExpenseDao

}
