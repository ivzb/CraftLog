package com.ivzb.craftlog.feature.cars.usecase

import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.domain.repository.CarRepository
import javax.inject.Inject

class DeleteCarUseCase @Inject constructor(
    private val repository: CarRepository
) {

    suspend fun deleteCar(expense: Car) {
        return repository.deleteCar(expense)
    }
}
