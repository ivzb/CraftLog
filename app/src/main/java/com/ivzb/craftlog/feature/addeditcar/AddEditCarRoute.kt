package com.ivzb.craftlog.feature.addeditcar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.domain.model.Car
import com.ivzb.craftlog.feature.addeditcar.viewmodel.AddEditCarState
import com.ivzb.craftlog.feature.addeditcar.viewmodel.AddEditCarViewModel
import com.ivzb.craftlog.navigation.navigateBack
import com.ivzb.craftlog.util.SnackbarUtil.showSnackbar

@Composable
fun AddEditCarRoute(
    car: Car?,
    navController: NavHostController,
    viewModel: AddEditCarViewModel = hiltViewModel()
) {
    val analyticsHelper = AnalyticsHelper.getInstance(LocalContext.current)
    AddEditCarScreen(car, navController, viewModel, analyticsHelper)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCarScreen(
    car: Car?,
    navController: NavHostController,
    viewModel: AddEditCarViewModel,
    analyticsHelper: AnalyticsHelper,
) {
    val id by rememberSaveable { mutableStateOf(car?.id ?: 0L) }
    var brand by rememberSaveable { mutableStateOf(car?.brand ?: "") }
    var model by rememberSaveable { mutableStateOf(car?.model ?: "") }
    var year by rememberSaveable { mutableStateOf(car?.year?.toString() ?: "") }
    val additionalData = remember { mutableStateMapOf<String, String>() }
    car?.additionalData?.forEach { (key, value) -> additionalData[key] = value }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel
            .isCarSaved
            .collect {
                navController.navigateBack()
                analyticsHelper.logEvent(AnalyticsEvents.ADD_EDIT_CAR_SAVED)
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                navigationIcon = {
                    FloatingActionButton(
                        onClick = {
                            analyticsHelper.logEvent(AnalyticsEvents.ADD_EDIT_CAR_ON_BACK_CLICKED)
                            navController.navigateBack()
                        },
                        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(id = if (id == 0L) R.string.add_car else R.string.edit_car),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp),
                onClick = {
                    validateCar(
                        id = id,
                        brand = brand,
                        model = model,
                        year = year,
                        additionalData = additionalData.toMap(),
                        onInvalidate = {
                            val invalidatedValue = context.getString(it)

                            showSnackbar(
                                context.getString(
                                    R.string.value_is_empty,
                                    invalidatedValue
                                )
                            )

                            val event = String.format(
                                AnalyticsEvents.ADD_EDIT_CAR_VALUE_INVALIDATED,
                                invalidatedValue
                            )

                            analyticsHelper.logEvent(event)
                        },
                        onValidate = { car ->
                            if (car.id == 0L) {
                                viewModel.addCar(AddEditCarState(car))
                            } else {
                                viewModel.editCar(AddEditCarState(car))
                            }

                            analyticsHelper.logEvent(AnalyticsEvents.ADD_EDIT_CAR_ON_SAVE_CLICKED)
                        },
                        viewModel = viewModel
                    )
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(id = R.string.brand),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = brand,
                onValueChange = { brand = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                placeholder = { Text(text = stringResource(R.string.brand_placeholder)) },
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = stringResource(id = R.string.model),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = model,
                onValueChange = { model = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                placeholder = { Text(text = stringResource(R.string.model_placeholder)) },
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = stringResource(id = R.string.year),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = year,
                onValueChange = { year = it },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text(text = stringResource(R.string.year_placeholder)) },
            )
        }
    }
}

private fun validateCar(
    id: Long,
    brand: String,
    model: String,
    year: String,
    additionalData: Map<String, String>,
    onInvalidate: (Int) -> Unit,
    onValidate: (Car) -> Unit,
    viewModel: AddEditCarViewModel
) {
    if (brand.isEmpty()) {
        onInvalidate(R.string.brand)
        return
    }

    if (model.isEmpty()) {
        onInvalidate(R.string.model)
        return
    }

    if ((year.toIntOrNull() ?: 0) < 1) {
        onInvalidate(R.string.year)
        return
    }

    val car = viewModel.createCar(
        id,
        brand,
        model,
        year.toInt(),
        additionalData
    )

    onValidate(car)
}
