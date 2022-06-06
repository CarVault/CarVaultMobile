package com.app.carvault.car.editCar

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.carvault.car.Car

@Suppress("DEPRECATION")
class EditCarTabCollectionAdapter (
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    val car: Car?
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                EditCarTabVehicleDataFragment(car)
            }
            1 -> {
                EditCarTabDocumentationFragment(car)
            }
            2 -> {
                EditCarTabPhotosFragment(car)
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}
