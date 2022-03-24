package com.app.carvault.ui.profile
import androidx.annotation.DrawableRes

data class Profile (
    var id: Long,
    var name: String,
    var email: String,
    var phone: String,
    @DrawableRes var img: Int? = null
)