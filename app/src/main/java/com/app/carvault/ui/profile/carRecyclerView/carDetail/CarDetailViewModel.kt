package com.app.carvault.ui.profile.carRecyclerView.carDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.carvault.ui.profile.carRecyclerView.data.Car
import com.app.carvault.ui.profile.carRecyclerView.data.CarDataSource

class CarDetailViewModel (private val datasource: CarDataSource) : ViewModel() {
    fun getCarForId(id: Long) : Car? {
        return datasource.getCarForId(id)
    }
    fun removeCar(car: Car) {
        datasource.removeCar(car)
    }
}

class CarDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarDetailViewModel(
                datasource = CarDataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}