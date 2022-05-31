package com.app.carvault.graphql

import com.apm.graphql.*
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
        response.data?.getUser?.let { query ->
            return User.fromGraphqlQuery(
                query.userFields,
                query.cars!!.mapNotNull { carQuery -> carQuery!!.carFields }
            )
        }
        return null
    }

    suspend fun getUserByUsername (username: String) : User? {
        val response = client.query(GetUserByUsernameQuery(username = username)).execute()
        response.data?.getUserByUsername?.let { query ->
            return User.fromGraphqlQuery(
                query.userFields,
                query.cars!!.mapNotNull { carQuery -> carQuery!!.carFields }
            )
        }
        return null
    }

    suspend fun getUserByEmail (email: String) : User? {
        val response = client.query(GetUserByEmailQuery(email = email)).execute()
        response.data?.getUserByEmail?.let { query ->
            return User.fromGraphqlQuery(
                query.userFields,
                query.cars!!.mapNotNull { carQuery -> carQuery!!.carFields }
            )
        }
        return null
    }


    fun getUserCars() : List<Car>? {
        return currentUser?.cars
    }

    suspend fun getCarById (id: Int) : Car?{
        val response = client.query(GetCarByIdQuery(id=id.toString())).execute()
        response.data?.getCarById?.let { query ->
            return Car.fromGraphqlQuery(
                query.carFields,
                query.owner!!.id!!.toLong()
            )
        }
        return null
    }

    suspend fun getCarByVin (vin: String) : Car?{
        val response = client.query(GetCarByVinQuery(vin = vin)).execute()
        response.data?.getCarByVin?.let { query ->
            return Car.fromGraphqlQuery(
                query.carFields,
                query.owner!!.id!!.toLong()
            )
        }
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