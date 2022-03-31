package com.app.carvault.user
import androidx.annotation.DrawableRes

data class User (
    val id: Long,
    val username: String,
    val password: String,
    var name: String,
    var email: String,
    var phone: String,
    var cars: List<Long>,
    var transactions: List<Long>,
    var profile_img: String
)