package com.app.carvault.graphql

import android.R
import android.os.Build
import androidx.annotation.RequiresApi
import com.apm.graphql.*
import com.apm.graphql.type.CarDocumentInput
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.app.carvault.car.Car
import com.app.carvault.documents.Document
import com.app.carvault.user.User

const val SERVER_URL = "http://carvault2-env.eba-c3vjpzfb.us-east-1.elasticbeanstalk.com/graphql"

class GraphqlClient private constructor() {

    private val client = ApolloClient.Builder().serverUrl(SERVER_URL).build()
    private var currentUser: User? = null

    var temporalCar: Car? = null

    fun setCurrentUser(user: User){
        this.currentUser = user
    }

    fun getCurrentUser() : User?{
        return currentUser
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
    @RequiresApi(Build.VERSION_CODES.O)

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

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
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

    suspend fun addUser(username: String, email: String, firstname: String?, surname: String, phone: String, profilePicture: String?): Long? {
        val newUserMutation = NewUserMutation(
            username=username,
            email=email,
            firstname = Optional.presentIfNotNull(firstname),
            surname = Optional.presentIfNotNull(surname),
            phone = phone,
            profilePicture = Optional.presentIfNotNull(profilePicture)
        )
        val response =  client.mutation(newUserMutation).execute()
        return response.data?.newUser?.id?.toLong()
    }

    suspend fun updateUser(userId: String, firstname: String? = null, surname: String? = null,
                           phone: String? = null, profilePicture: String? = null): Long? {
        val updateUserMutation = UpdateUserMutation(
            userId=userId,
            firstname = Optional.presentIfNotNull(firstname),
            surname = Optional.presentIfNotNull(surname),
            phone = Optional.presentIfNotNull(phone),
            profilePicture = Optional.presentIfNotNull(profilePicture)
        )
        val response =  client.mutation(updateUserMutation).execute()
        return response.data?.updateUser?.id?.toLong()
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
            color = Optional.presentIfNotNull(color),
        )
        val response =  client.mutation(newCarMutation).execute()
        return response.data?.newCar?.id?.toLong()
    }

    suspend fun updateCar(carId: String, vin: String? = null, brand: String? = null, model: String? = null, description: String? = null,
                       kilometers: Int? = null, horsepower: Int? = null, year: Int? = null, address: String? = null,
                       manufacturer: String? = null, origin: String? = null, fuel: String? = null, color: String? = null, images: List<String>? = null): Long? {
        val updateCarMutation = UpdateCarMutation(
            carId = carId,
            vin = Optional.presentIfNotNull(vin),
            brand = Optional.presentIfNotNull(brand),
            model = Optional.presentIfNotNull(model),
            description = Optional.presentIfNotNull(description),
            kilometers = Optional.presentIfNotNull(kilometers),
            horsepower = Optional.presentIfNotNull(horsepower),
            year = Optional.presentIfNotNull(year),
            address = Optional.presentIfNotNull(address),
            manufacturer = Optional.presentIfNotNull(manufacturer),
            origin = Optional.presentIfNotNull(origin),
            fuel = Optional.presentIfNotNull(fuel),
            color = Optional.presentIfNotNull(color),
            images = Optional.presentIfNotNull(images)
        )
        val response =  client.mutation(updateCarMutation).execute()
        return response.data?.updateCar?.id?.toLong()
    }

    suspend fun transferCar(carId: String, userId: String) : Long?{
        val transferMutation = TransferMutation(
            userId = userId,
            carId = carId
        )
        val response =  client.mutation(transferMutation).execute()
        return response.data?.transferCar?.id?.toLong()
    }

    suspend fun updateDocuments(carId: String, documents: List<Document>): Long?{
        val updateDocMutation = UpdateDocumentsMutation(
            carId = carId,
            documents = documents.map { it.toCarDocumenInput() }
        )
        val response = client.mutation(updateDocMutation).execute()
        return response.data?.updateCarDocuments?.id?.toLong()
    }

    suspend fun deleteDocuments(carId: String): Long?{
        val deleteDocsMutation = DeleteDocumentsMutation(
            carId = carId,
        )
        val response = client.mutation(deleteDocsMutation).execute()
        return response.data?.updateCarDocuments?.id?.toLong()
    }

    suspend fun deleteImages(carId: String): Long?{
        val deleteImgsMutation = DeleteImagesMutation(
            carId = carId,
        )
        val response = client.mutation(deleteImgsMutation).execute()
        return response.data?.updateCar?.id?.toLong()
    }

    suspend fun deleteCar(carId: String){
        val deleteCar = DeleteCarMutation(
            carId = carId,
        )
        client.mutation(deleteCar).execute()
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