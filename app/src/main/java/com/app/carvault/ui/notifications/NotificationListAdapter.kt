package com.app.carvault.ui.notifications

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R

class NotificationListAdapter () :
    ListAdapter<Notification, NotificationListAdapter.NotifViewHolder>(NotificationDiffCallback) {

    class NotifViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val date: TextView = view.findViewById(R.id.notif_date)
        private val info: TextView = view.findViewById(R.id.info)

        fun bind(notif: Notification) {
            date.text = notif.date
            info.text = notif.info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return NotifViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.id == newItem.id
    }
}