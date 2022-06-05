package com.app.carvault.car.carDetail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.carvault.car.Car
import com.app.carvault.transaction.TransactionListFragment

@Suppress("DEPRECATION")
class CarTabCollectionAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    val car: Car?
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CarTabDetailsFragment(car)
            }
            1 -> {
                CarDocumentsFragment(car)
            }
            2 -> {
                TransactionListFragment(car)
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}