package com.ivzb.craftlog.feature.cars

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.feature.cars.viewmodel.CarsViewModel
import com.ivzb.craftlog.navigation.navigateToAddCar
import com.ivzb.craftlog.navigation.navigateToCarDetail
import com.ivzb.craftlog.navigation.navigateToEditCar
import com.ivzb.craftlog.ui.components.CategoryTitleBar

@Composable
fun CarsRoute(
    navController: NavHostController,
    viewModel: CarsViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.loadCars()
    }

    CarsScreen(
        navController,
        viewModel,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarsScreen(
    navController: NavHostController,
    viewModel: CarsViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.cars),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
            )
        },
        bottomBar = { },
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                CategoryTitleBar(
                    title = stringResource(id = R.string.cars),
                    showMoreButton = false,
                    onAddClick = {
                        navController.navigateToAddCar()
                    },
                )
            }

            val cars = viewModel.state.cars
                .sortedByDescending { it.id }
                .map { CarListItem.CarItem(it) }

            if (cars.isEmpty()) {
                item {
                    CarEmptyView()
                }
            }

            items(
                items = cars,
                itemContent = {
                    CarListItem(
                        it,
                        onCarDetail = { car ->
                            navController.navigateToCarDetail(car)
                        },
                        onEditCar = { car ->
                            navController.navigateToEditCar(car)
                        },
                        onDeleteCar = { car ->
                            viewModel.deleteCar(car)
                            viewModel.loadCars()
                        }
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.CarListItem(
    it: CarListItem,
    onCarDetail: (Car) -> Unit,
    onEditCar: (Car) -> Unit,
    onDeleteCar: (Car) -> Unit
) {
    when (it) {
        is CarListItem.CarItem -> {
            CarCard(
                modifier = Modifier.animateItemPlacement(),
                car = it.car,
                navigateToCarDetail = { car ->
                    onCarDetail(car)
                },
                onEdit = { car ->
                    onEditCar(car)
                },
                onDelete = { car ->
                    onDeleteCar(car)
                }
            )
        }
    }
}

@Composable
fun CarEmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(id = R.string.no_cars_yet),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

sealed class CarListItem(open val id: Long) {

    data class CarItem(val car: Car, val offset: Long = 0) :
        CarListItem((car.id ?: 0) + offset)

}
