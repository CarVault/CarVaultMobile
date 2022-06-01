package com.app.carvault.transaction

import android.content.Context
import com.app.carvault.graphql.GraphqlClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TransactionDataSource private constructor (private val context: Context) {

    fun loadTransactions(userTransactions: List<Long>): List<Transaction> {
        val gson = Gson()
        val listTransType = object : TypeToken<List<Transaction>>() {}.type
        val transListJson = context.assets.open("transactions.json").bufferedReader().use{ it.readText() }
        val transList: List<Transaction> = gson.fromJson(transListJson, listTransType)
        return transList.filter { it.id in userTransactions }
    }

    suspend fun getTransactionInfo (t: Transaction) : TransactionInfo {
        val from = GraphqlClient.getInstance().getUserById(t.from_id.toInt())?.username
        val to = GraphqlClient.getInstance().getUserById(t.to_id.toInt())?.username
        val date = t.date
        val car = GraphqlClient.getInstance().getCarById(t.car.toInt())?.model
        val sha = t.sha256
        return TransactionInfo(from?:"", to?:"", date, car?:"", sha)
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

