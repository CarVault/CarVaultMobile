package com.app.carvault.car.carList

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.Util
import com.app.carvault.car.Car

class CarAdapter (private val onClick: (Car) -> Unit) :
    ListAdapter<Car, CarAdapter.CarViewHolder>(CarDiffCallback) {

    /* Car view holder */
    class CarViewHolder(view: View, val onClick: (Car) -> Unit) : RecyclerView.ViewHolder(view) {
        private val carNameTextView: TextView = view.findViewById(R.id.item_car_name)
        private val carVINTextView: TextView = view.findViewById(R.id.item_car_vin)
        private val carImgView: ImageView = view.findViewById(R.id.item_car_img)

        fun bind(car: Car) {
            carNameTextView.text = car.brand
            carVINTextView.text = car.VIN
            val bitMapImage = Util.bitmapImageFromString64(car.img.firstOrNull(), false)
            if (bitMapImage != null) {
                carImgView.setImageBitmap(bitMapImage)
            } else {
                carImgView.setImageResource(R.drawable.default_cars)
            }
            itemView.setOnClickListener {
                onClick(car)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car_list, parent, false)
        return CarViewHolder(layoutInflater, onClick)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

object CarDiffCallback : DiffUtil.ItemCallback<Car>() {
    override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem.id == newItem.id
    }
}