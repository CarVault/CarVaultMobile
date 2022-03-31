package com.app.carvault.car.carList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.car.CarDataSource
import kotlin.random.Random

class CarListViewModel(val dataSource: CarDataSource) : ViewModel() {

    val carsLiveData = dataSource.getCarList()
/*
    fun insertCar(carName: String?, carVIN: String?) {
        if (carName == null || carVIN == null) {
            return
        }
        val image = R.drawable.default_cars
        val newCar = Car(
            id = Random.nextLong(),
            name = carName,
            VIN = carVIN,
            img = image
        )
        dataSource.addCar(newCar)
    }

 */
}

class CarListViewModelFactory(private val context: Context, private val userCars : List<Long>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarListViewModel(
                dataSource = CarDataSource.getDataSource(context, userCars)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}