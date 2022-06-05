package com.app.carvault.car.addCar

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.carvault.car.Car

@Suppress("DEPRECATION")
class AddCarTabCollectionAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    ) :
    FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    AddCarTabVehicleData()
                }
                1 -> {
                    AddCarTabDocumentationFragment()
                }
                2 -> {
                    AddCarTabPhotoFragment()
                }
                else -> getItem(position)
            }
        }
        override fun getCount(): Int {
            return totalTabs
        }
    }