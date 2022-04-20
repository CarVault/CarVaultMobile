package com.app.carvault.transaction

data class TransactionInfo(
    var from_name: String,
    var to_name: String,
    var date: String,
    var car_name: String,
    var sha256: String
)
