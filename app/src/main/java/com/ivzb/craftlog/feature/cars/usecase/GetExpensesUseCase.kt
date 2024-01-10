package com.ivzb.craftlog.feature.cars.usecase

import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarsUseCase @Inject constructor(
    private val repository: CarRepository
) {

    suspend fun getCars(): Flow<List<Car>> {
        return repository.getAllCars()
    }
}
