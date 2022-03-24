package com.app.carvault.ui.profile.carRecyclerView.data
import androidx.annotation.DrawableRes

data class Car(
    var id: Long,
    var name: String,
    var VIN: String,
    @DrawableRes var img: Int?
)
