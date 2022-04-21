package com.app.carvault.ui.notifications

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NotificationDataSource private constructor (private val context: Context) {

    fun loadNotifications(userNotifications: List<Long>): List<Notification> {
        val gson = Gson()
        val listNotifType = object : TypeToken<List<Notification>>() {}.type
        val notifListJson = context.assets.open("notifications.json").bufferedReader().use{ it.readText() }
        val notifList: List<Notification> = gson.fromJson(notifListJson, listNotifType)
        return notifList.filter { it.id in userNotifications }
    }

    companion object {
        private var INSTANCE: NotificationDataSource? = null

        fun getDataSource(context: Context): NotificationDataSource {
            return synchronized(NotificationDataSource::class) {
                val newInstance = INSTANCE ?: NotificationDataSource(context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}