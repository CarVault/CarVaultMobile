package com.app.carvault.car.carDetail

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.app.carvault.R
import java.util.*

class CarImagesAdapter(val context: Context, val images: List<Bitmap?>) : PagerAdapter() {
    // on below line we are creating a method
    // as get count to return the size of the list.
    private var showDefault = false

    override fun getCount(): Int {
        if (images.isEmpty()){
            showDefault = true
            return 1
        }
        return images.size
    }

    // on below line we are returning the object
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    // on below line we are initializing
    // our item and inflating our layout file
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // on below line we are initializing
        // our layout inflater.
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView: View = mLayoutInflater.inflate(R.layout.car_images_item, container, false)

        val imageView: ImageView = itemView.findViewById<View>(R.id.image) as ImageView
        if (showDefault){
            imageView.setImageResource(R.drawable.default_cars)
        }else{
            images[position]?.let {
                imageView.setImageBitmap(images[position])
            }
        }
        Objects.requireNonNull(container).addView(itemView)

        return itemView
    }

    // on below line we are creating a destroy item method.
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // on below line we are removing view
        container.removeView(`object` as RelativeLayout)
    }
}