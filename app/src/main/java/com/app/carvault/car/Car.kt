package com.app.carvault.car
import androidx.annotation.DrawableRes
import java.time.Year

data class Car(
    var id: Long,
    var name: String,
    var VIN: String,
    var manufacturer: String,
    var year: Int,
    var horsepower: Int,
    var origin: String,
    var address: String,
    var kms: Int,
    var fuel: String,
    var color: String,
    var description: String,
    var img: String
)
