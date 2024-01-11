package com.ivzb.craftlog.feature.addeditcar.usecase

import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.domain.repository.CarRepository
import javax.inject.Inject

class AddCarUseCase @Inject constructor(
    private val repository: CarRepository
) {
    suspend fun addCar(car: Car) {
        repository.insertCar(car)
    }
}
