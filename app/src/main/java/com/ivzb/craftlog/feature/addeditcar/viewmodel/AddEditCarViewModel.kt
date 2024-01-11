package com.ivzb.craftlog.feature.addeditcar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.feature.addeditcar.usecase.AddCarUseCase
import com.ivzb.craftlog.feature.addeditcar.usecase.EditCarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddEditCarViewModel @Inject constructor(
    private val addCarUseCase: AddCarUseCase,
    private val editCarUseCase: EditCarUseCase,
) : ViewModel() {

    private val _isCarSaved = MutableSharedFlow<Unit>()
    val isCarSaved = _isCarSaved.asSharedFlow()

    fun createCar(
        id: Long,
        brand: String,
        model: String,
        year: Int,
        additionalData: Map<String, String>
    ): Car {
        return Car(
            id = id,
            brand = brand,
            model = model,
            year = year,
            date = Date(),
            additionalData = additionalData
        )
    }

    fun addCar(state: AddEditCarState) {
        viewModelScope.launch {
            val car = state.car
            val carAdded = addCarUseCase.addCar(car)
            _isCarSaved.emit(carAdded)
        }
    }

    fun editCar(state: AddEditCarState) {
        viewModelScope.launch {
            val car = state.car
            val carEdited = editCarUseCase.editCar(car)
            _isCarSaved.emit(carEdited)
        }
    }

}
