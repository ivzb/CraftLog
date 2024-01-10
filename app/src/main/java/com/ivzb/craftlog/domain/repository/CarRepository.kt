package com.ivzb.craftlog.domain.repository

import com.ivzb.craftlog.domain.model.Car
import kotlinx.coroutines.flow.Flow

interface CarRepository {

    suspend fun insertCar(car: Car)

    suspend fun deleteCar(car: Car)

    suspend fun updateCar(car: Car)
    
    suspend fun getAllCars(): Flow<List<Car>>

}
