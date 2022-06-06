package com.app.carvault.car.editCar

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
class EditCarTabCollectionAdapter (
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                EditCarTabVehicleDataFragment()
            }
            1 -> {
                EditCarTabDocumentationFragment()
            }
            2 -> {
                EditCarTabPhotosFragment()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}
