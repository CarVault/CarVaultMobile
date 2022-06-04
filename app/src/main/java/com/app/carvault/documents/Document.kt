package com.app.carvault.documents

data class Document(
    val id: Long,
    val name: String,
    var date: String,
    var content: String,
)
