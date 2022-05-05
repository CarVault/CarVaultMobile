package com.app.carvault.graphql

import com.apm.graphql.GetCarByIdQuery
import com.apm.graphql.UserQuery
import com.apollographql.apollo3.ApolloClient
import com.app.carvault.car.Car
import com.app.carvault.user.User

const val SERVER_URL = "http://carvault2-env.eba-c3vjpzfb.us-east-1.elasticbeanstalk.com/graphql"

class GraphqlClient private constructor() {

    private val client = ApolloClient.Builder().serverUrl(SERVER_URL).build()
    private var currentUser: User? = null

    fun setCurrentUser(user: User){
        this.currentUser = user
    }

    fun getCurrentUser() : User?{
        return currentUser
    }

    suspend fun getUserById (id: Int) : User?{
        val response = client.query(UserQuery(id=id.toString())).execute()
        return User.fromGraphqlQuery(response.data?.getUser)
    }

    suspend fun getUserByEmail (email: String) : User? {
        // Solucion temporal
        return getUserById(1)
    }

    suspend fun getCarById (id: Int) : Car?{
        val response = client.query(GetCarByIdQuery(id=id.toString())).execute()
        return Car.fromGraphqlQuery(response.data?.getCarById)
    }

    suspend fun getUserCars() : List<Car?> {
        currentUser?.cars.let {
            val out = mutableListOf<Car?>()
            for (carId in it!!){
                out.add(getCarById(carId.toInt()))
            }
            return out
        }
    }

    suspend fun getCarByVin (vin: String) : Car?{
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