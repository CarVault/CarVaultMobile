package com.app.carvault.ui.profile.carRecyclerView.data

import android.content.res.Resources
import com.app.carvault.R

fun getMockCarList(resources: Resources): List<Car> {
    return listOf(
        Car(1, "BMW Serie 4 M4A", "JN8AS1MU0CM120061", R.drawable.coche),
        Car(2, "Otro BMW", "AAAAAAAAAAAAAAAAA", R.drawable.coche),
        Car(3, "Tercer BMW", "BBBBBBBBBBBBBBBBBBB", R.drawable.coche)
    )
}