package com.app.carvault.car

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CarDataSource private constructor (private val context: Context) {

    fun loadCarList(userCars: List<Long>): List<Car> {
        val gson = Gson()
        val listCarType = object : TypeToken<List<Car>>() {}.type
        val carListJson = context.assets.open("cars.json").bufferedReader().use{ it.readText() }
        val carList: List<Car> = gson.fromJson(carListJson, listCarType)
        return carList.filter { it.id in userCars }
    }

    fun getCarForId(id: Long?): Car? {
        id?.let {
            val gson = Gson()
            val listCarType = object : TypeToken<List<Car>>() {}.type
            val carListJson = context.assets.open("cars.json").bufferedReader().use{ it.readText() }
            val carList: List<Car> = gson.fromJson(carListJson, listCarType)
            return carList.firstOrNull { it.id == id }
        }
        return null
    }

    /*
    fun addCar(car: Car) {
        val currentList = carsLiveData.value
        if (currentList == null) {
            carsLiveData.postValue(listOf(car))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, car)
            carsLiveData.postValue(updatedList)
        }
    }

    fun removeCar(car: Car) {
        val currentList = carsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(car)
            carsLiveData.postValue(updatedList)
        }
    }

    /* Returns car given an ID. */
    fun getCarForId(id: Long): Car? {
        carsLiveData.value?.let { cars ->
            return cars.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getCarList(): LiveData<List<Car>> {
        return carsLiveData
    }
     */

    companion object {
        private var INSTANCE: CarDataSource? = null

        fun getDataSource(context: Context): CarDataSource {
            return synchronized(CarDataSource::class) {
                val newInstance = INSTANCE ?: CarDataSource(context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}