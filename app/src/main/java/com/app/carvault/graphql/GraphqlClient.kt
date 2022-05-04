package com.app.carvault.graphql

import com.apollographql.apollo3.ApolloClient
import com.app.carvault.car.Car
import com.app.carvault.user.User
import com.app.carvault.user.UserDataSource

const val SERVER_URL = "http://carvault2-env.eba-c3vjpzfb.us-east-1.elasticbeanstalk.com/graphiql"

class GraphqlClient private constructor() {

    val client = ApolloClient.Builder().serverUrl(SERVER_URL).build()
    private var currentUser: User? = null

    fun setCurrentUser(user: User){
        this.currentUser = user
    }

    fun getCurrentUser() : User?{
        return currentUser
    }

    fun getUserById (id: Int) : User?{
        return null
    }

    fun getUserByEmail (email: String) : User? {
        // Solucion temporal
        return User(
            id = 5,
            username = "mframil",
            password = "mframil",
            name = "Manuel Framil",
            email = "manuframil1999@gmail.com",
            phone = "665 765 123",
            cars = listOf(1,2,3),
            transactions = listOf(1,2),
            notifications = listOf(1,2),
            profile_img = ""
        )
    }

    fun getCarById (id: Int) : Car?{
        return null
    }

    fun getCarByVin (vin: String) : Car?{
        return null
    }

    companion object {
        private var INSTANCE: GraphqlClient? = null

        fun getInstance(): GraphqlClient {
            return synchronized(GraphqlClient::class) {
                val newInstance = INSTANCE ?: GraphqlClient()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}