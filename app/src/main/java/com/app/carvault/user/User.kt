package com.app.carvault.user

import com.apm.graphql.fragment.CarFields
import com.apm.graphql.fragment.UserFields
import com.app.carvault.car.Car

data class User (
    val id: Long,
    val username: String,
    var firstname: String,
    var surname: String,
    var email: String,
    var phone: String,
    var cars: List<Car>,
    var transactions: List<Long>,
    var notifications: List<Long>,
    var profilePicture: String
) {
    companion object {
        fun fromGraphqlQuery(userFields: UserFields, carFields: List<CarFields>): User{
            return User(
                id = userFields.id!!.toLong(),
                username = userFields.username!!,
                firstname = userFields.firstname!!,
                surname = userFields.surname!!,
                email = userFields.email!!,
                phone = userFields.phone!!.toInt().toString(),
                cars = carFields.map { Car.fromGraphqlQuery(it, userFields.id.toLong()) },
                transactions = listOf(1,2,3),
                notifications = listOf(1,2,3),
                profilePicture = userFields.profilePicture!!,
            )
        }
    }
}
