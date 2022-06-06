package com.app.carvault.documents

import com.apm.graphql.type.CarDocumentInput
import com.apollographql.apollo3.api.Optional
import java.time.LocalDateTime

data class Document(
    val id: Long,
    val name: String?,
    val type: String?,
    var date: LocalDateTime?,
    var content: String?,
){
    fun toCarDocumenInput(): CarDocumentInput {
        return CarDocumentInput(
            content = Optional.presentIfNotNull(this.content),
            documentType = Optional.presentIfNotNull(this.type),
            documentName = Optional.presentIfNotNull(this.name)
        )
    }
}