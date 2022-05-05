package com.app.carvault.transaction

import android.content.Context
import com.app.carvault.car.CarDataSource
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

    fun getTransactionInfo (t: Transaction) : TransactionInfo {
        val from = UserDataSource.getDataSource(context).getUser(t.from_id)?.username
        val to = UserDataSource.getDataSource(context).getUser(t.to_id)?.username
        val date = t.date
        val car = CarDataSource.getDataSource(context).getCarForId(t.car)?.model
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

