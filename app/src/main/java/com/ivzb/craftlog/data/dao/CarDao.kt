package com.ivzb.craftlog.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ivzb.craftlog.data.entity.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(carEntity: CarEntity): Long

    @Delete
    suspend fun deleteCar(carEntity: CarEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCar(carEntity: CarEntity)

    @Query(
        """
            SELECT *
            FROM carentity
        """
    )
    fun getAllCars(): Flow<List<CarEntity>>

}
