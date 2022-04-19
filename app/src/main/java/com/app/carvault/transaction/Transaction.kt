package com.app.carvault.transaction

data class Transaction (
    var id: Long,
    var from: Long,
    var to: Long,
    var date: String,
    var car: Long,
    var sha256: String
    )