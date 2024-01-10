package com.ivzb.craftlog.data.repository

import com.ivzb.craftlog.data.dao.CarDao
import com.ivzb.craftlog.data.mapper.toCar
import com.ivzb.craftlog.data.mapper.toCarEntity
import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CarRepositoryImpl(
    private val dao: CarDao
) : CarRepository {

    override suspend fun insertCar(expense: Car) {
        val entity = expense.toCarEntity()
        dao.insertCar(entity)
    }

    override suspend fun deleteCar(expense: Car) {
        dao.deleteCar(expense.toCarEntity())
    }

    override suspend fun updateCar(expense: Car) {
        dao.updateCar(expense.toCarEntity())
    }

    override suspend fun getAllCars(): Flow<List<Car>> {
        return dao.getAllCars().map { entities ->
            entities.map { it.toCar() }
        }
    }

}
