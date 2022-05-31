package com.app.carvault.car
import com.apm.graphql.fragment.CarFields

data class Car(
    var id: Long,
    var brand: String,
    var model: String,
    var VIN: String,
    var description: String,
    var kms: Int,
    var year: Int,
    var address: String,
    var manufacturer: String,
    var origin: String,
    var fuel: String,
    var color: String,
    var img: String,
    var owner: Long
){
    companion object {
        fun fromGraphqlQuery(carFields: CarFields, owner: Long): Car{
            return Car(
                id = carFields.id!!.toLong(),
                brand = carFields.brand!!,
                model = carFields.model!!,
                VIN = carFields.vin!!,
                manufacturer = carFields.manufacturer!!,
                year = carFields.year!!,
                origin = carFields.origin!!,
                address = carFields.address!!,
                kms = carFields.kilometers!!.toInt(),
                fuel = carFields.fuel!!,
                description = carFields.description!!,
                color = carFields.color!!,
                img = "",
                owner = owner
            )
        }
    }
}

