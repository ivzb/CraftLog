package com.ivzb.craftlog.data.mapper

import com.ivzb.craftlog.data.entity.CarEntity
import com.ivzb.craftlog.domain.model.Car

fun CarEntity.toCar(): Car {
    return Car(
        id = id,
        brand = brand,
        model = model,
        year = year,
        date = date,
        additionalData = additionalData
    )
}

fun Car.toCarEntity(): CarEntity {
    return CarEntity(
        id = id ?: 0L,
        brand = brand,
        model = model,
        year = year,
        date = date,
        additionalData = additionalData
    )
}
