package com.ivzb.craftlog.feature.cars.viewmodel

import com.ivzb.craftlog.domain.model.Car

data class CarsState(
    val cars: List<Car> = emptyList()
)
