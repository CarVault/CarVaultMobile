package com.app.carvault.user

import com.apm.graphql.UserQuery
import com.app.carvault.car.Car
import com.app.carvault.transaction.Transaction
import com.app.carvault.ui.notifications.Notification

data class User (
    val id: Long,
    val username: String,
    var firstname: String,
    var surname: String,
    var email: String,
    var phone: String,
    var cars: List<Long>,
    var transactions: List<Long>,
    var notifications: List<Long>,
    var profilePicture: String
) {
    companion object {
        fun fromGraphqlQuery(query: UserQuery.GetUser?): User?{
            query?.let {
                return User(
                    id = query.id!!.toLong(),
                    username = query.username!!,
                    firstname = query.firstname!!,
                    surname = query.surname!!,
                    email = query.email!!,
                    phone = query.phone!!.toInt().toString(),
                    cars = listOf(1,2,3),
                    transactions = listOf(1,2,3),
                    notifications = listOf(1,2,3),
                    profilePicture = query.profilePicture!!,
                )}
            return null
        }
    }
}