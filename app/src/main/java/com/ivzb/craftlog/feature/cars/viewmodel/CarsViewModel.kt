package com.ivzb.craftlog.feature.cars.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.feature.cars.usecase.DeleteCarUseCase
import com.ivzb.craftlog.feature.cars.usecase.GetCarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val getCarsUseCase: GetCarsUseCase,
    private val deleteCarUseCase: DeleteCarUseCase,
) : ViewModel() {

    var state by mutableStateOf(CarsState())
        private set

    fun loadCars() {
        viewModelScope.launch {
            getCarsUseCase.getCars().onEach { carsList ->
                state = state.copy(
                    cars = carsList
                )
            }.launchIn(viewModelScope)
        }
    }

    fun deleteCar(car: Car) {
        viewModelScope.launch {
            deleteCarUseCase.deleteCar(car.copy())
        }
    }

}
