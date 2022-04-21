package com.app.carvault.ui.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.user.UserDataSource

class NotificationsActivity : FragmentActivity() {

    private lateinit var userDataSource : UserDataSource
    private lateinit var notificationDataSource: NotificationDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        userDataSource = UserDataSource.getDataSource(this)
        notificationDataSource = NotificationDataSource.getDataSource(this)

        // Inflate the layout for this fragment
        val notifAdapter = NotificationListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.notification_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = notifAdapter
        notifAdapter.submitList(notificationDataSource.loadNotifications(userDataSource.getCurrentUser().notifications))
    }
}