package com.app.carvault.graphql

import com.apm.graphql.*
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
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

    suspend fun addUser(username: String, email: String, firstname: String?, surname: String, phone: Double, profilePicture: String?): Long? {
        val newUserMutation = NewUserMutation(
            username=username,
            email=email,
            firstname = Optional.presentIfNotNull(firstname),
            surname = Optional.presentIfNotNull(surname),
            phone = phone,
            profilePicture = Optional.presentIfNotNull(profilePicture)
        )
        val response =  client.mutation(newUserMutation).execute()
        print(response)
        return response.data?.newUser?.id?.toLong()
    }

    suspend fun addCar(userId: String, vin: String, brand: String, model: String, description: String?,
                        kilometers: Int?, horsepower: Int?, year: Int?, address: String?,
                        manufacturer: String?, origin: String?, fuel: String?, color: String?): Long? {
        val newCarMutation = NewCarMutation(
            userId = userId,
            vin = vin,
            brand = brand,
            model = model,
            description = Optional.presentIfNotNull(description),
            kilometers = Optional.presentIfNotNull(kilometers),
            horsepower = Optional.presentIfNotNull(horsepower),
            year = Optional.presentIfNotNull(year),
            address = Optional.presentIfNotNull(address),
            manufacturer = Optional.presentIfNotNull(manufacturer),
            origin = Optional.presentIfNotNull(origin),
            fuel = Optional.presentIfNotNull(fuel),
            color = Optional.presentIfNotNull(color)
        )
        val response =  client.mutation(newCarMutation).execute()
        return response.data?.newCar?.id?.toLong()
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