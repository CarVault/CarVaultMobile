package com.app.carvault.car
import com.apm.graphql.GetCarByIdQuery


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
    var img: String
){
    companion object {
        fun fromGraphqlQuery(query: GetCarByIdQuery.GetCarById?): Car?{
            query?.let {
                return Car(
                    id = query.id!!.toLong(),
                    brand = query.brand!!,
                    model = query.model!!,
                    VIN = query.vin!!,
                    manufacturer = query.manufacturer!!,
                    year = query.year!!,
                    origin = query.origin!!,
                    address = query.address!!,
                    kms = query.kilometers!!.toInt(),
                    fuel = query.fuel!!,
                    description = query.description!!,
                    color = query.color!!,
                    img = ""
                )}
            return null
        }
    }
}
