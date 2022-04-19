package com.app.carvault.transaction

import android.content.Context
import com.app.carvault.car.Car
import com.app.carvault.user.UserDataSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TransactionDataSource private constructor (private val context: Context) {

    fun loadTransactions(userTransactions: List<Long>): List<Transaction> {
        val gson = Gson()
        val listTransType = object : TypeToken<List<Transaction>>() {}.type
        val transListJson = context.assets.open("transactions.json").bufferedReader().use{ it.readText() }
        val transList: List<Transaction> = gson.fromJson(transListJson, listTransType)
        return transList.filter { it.id in userTransactions }
    }

    companion object {
        private var INSTANCE: TransactionDataSource? = null

        fun getDataSource(context: Context): TransactionDataSource {
            return synchronized(TransactionDataSource::class) {
                val newInstance = INSTANCE ?: TransactionDataSource(context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}

