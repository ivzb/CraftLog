package com.ivzb.craftlog.feature.addeditcar.usecase

import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.domain.repository.CarRepository
import javax.inject.Inject

class EditCarUseCase @Inject constructor(
    private val repository: CarRepository
) {

    suspend fun editCar(car: Car) {
        return repository.updateCar(car)
    }
}
