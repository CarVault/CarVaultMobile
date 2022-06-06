package com.app.carvault.car
import android.os.Build
import androidx.annotation.RequiresApi
import com.apm.graphql.fragment.CarFields
import com.app.carvault.documents.Document
import com.app.carvault.transaction.Transaction
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Car(
    var id: Long,
    var brand: String,
    var model: String,
    var VIN: String,
    var description: String?,
    var kms: Int?,
    var year: Int?,
    var address: String?,
    var manufacturer: String?,
    var origin: String?,
    var fuel: String?,
    var color: String?,
    var img: List<String>?,
    var documents: List<Document>?,
    var transactions: List<Transaction>?,
    var owner: Long
){
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun fromGraphqlQuery(carFields: CarFields, owner: Long): Car{
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return Car(
                id = carFields.id!!.toLong(),
                brand = carFields.brand!!,
                model = carFields.model!!,
                VIN = carFields.vin!!,
                manufacturer = carFields.manufacturer,
                year = carFields.year,
                origin = carFields.origin,
                address = carFields.address,
                kms = carFields.kilometers,
                fuel = carFields.fuel,
                description = carFields.description,
                color = carFields.color,
                img = carFields.images?.mapNotNull { it?.content.toString() },
                documents = carFields.documents!!.mapNotNull {
                    Document(
                        id = it?.id!!.toLong(),
                        content = it.content,
                        name = it.documentName,
                        date = LocalDateTime.parse(it.date!!.substring(0, it.date.indexOfFirst { c -> c == '.' }), pattern),
                        type = it.documentType
                    )},
                transactions = carFields.transactions?.mapNotNull { Transaction(it!!.hash!!) },
                owner = owner
            )
        }
    }
}

