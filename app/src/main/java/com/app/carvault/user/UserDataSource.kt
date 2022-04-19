package com.app.carvault.user

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class UserDataSource private constructor(context: Context){

    private lateinit var user: User
    private val userListJson = context.assets.open("users.json").bufferedReader().use{ it.readText() }

    fun getCurrentUser(): User {
        return user
    }

    fun createUser(user: User){

    }

    fun login(email: String, pass: String): Boolean {
        val gson = Gson()
        val listUserType = object : TypeToken<List<User>>() {}.type
        val users: List<User> = gson.fromJson(userListJson, listUserType)
        val logged = users.firstOrNull { u -> u.email == email.trim() && u.password == pass.trim() }
        if (logged != null) {
            user = logged
            return true
        }
        return false
    }


    fun getUser(id: Long): User? {
        val gson = Gson()
        val listUserType = object : TypeToken<List<User>>() {}.type
        val users: List<User> = gson.fromJson(userListJson, listUserType)
        return users.firstOrNull { u -> u.id == id }
    }

    fun updateUser(newName: String?, newEmail: String?, newPhone: String?): Boolean{
        if (newName != null)  { user.name = newName   }
        if (newEmail != null) { user.email = newEmail }
        if (newPhone != null) { user.phone = newPhone }
        return true
    }

    companion object {
        private var INSTANCE: UserDataSource? = null

        fun getDataSource(context: Context): UserDataSource {
            return synchronized(UserDataSource::class) {
                val newInstance = INSTANCE ?: UserDataSource(context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }

}