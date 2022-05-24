package com.app.carvault.ui.notifications

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.graphql.GraphqlClient


class NotificationsActivity : AppCompatActivity() {

    private lateinit var notificationDataSource: NotificationDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        notificationDataSource = NotificationDataSource.getDataSource(this)

        // Inflate the layout for this fragment
        val notifAdapter = NotificationListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.notification_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = notifAdapter
        notifAdapter.submitList(
            notificationDataSource.loadNotifications(
                GraphqlClient.getInstance().getCurrentUser()!!.notifications
            )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}