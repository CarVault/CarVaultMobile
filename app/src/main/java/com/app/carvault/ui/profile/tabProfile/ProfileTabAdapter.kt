package com.app.carvault.ui.profile.tabProfile

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.carvault.car.carList.CarListFragment
import com.app.carvault.transaction.TransactionListFragment

class ProfileTabAdapter (
        var context: Context,
        fm: FragmentManager,
        var totalTabs: Int
    ) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CarListFragment()
            }
            1 -> {
                TransactionListFragment()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}