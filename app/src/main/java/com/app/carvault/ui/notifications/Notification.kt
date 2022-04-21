package com.app.carvault.ui.notifications

data class Notification(
    val id: Long,
    val date: String,
    val type: String,
    var info: String,
    var seen: Boolean
)
