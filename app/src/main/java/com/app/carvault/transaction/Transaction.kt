package com.app.carvault.transaction

data class Transaction (
    var id: Long,
    var from_id: Long,
    var to_id: Long,
    var date: String,
    var car: Long,
    var sha256: String
    )