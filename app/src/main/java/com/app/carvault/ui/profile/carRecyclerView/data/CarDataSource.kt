package com.app.carvault.ui.profile.carRecyclerView.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CarDataSource(resources: Resources) {
    private val initialCarList = getMockCarList(resources)
    private val carsLiveData = MutableLiveData(initialCarList)

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

    companion object {
        private var INSTANCE: CarDataSource? = null

        fun getDataSource(resources: Resources): CarDataSource {
            return synchronized(CarDataSource::class) {
                val newInstance = INSTANCE ?: CarDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}