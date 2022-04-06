package com.app.carvault.car.carDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.carvault.R
import com.app.carvault.car.Car

class CarTabDescriptionFragment (val car: Car?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_car_tab_description, container, false)
        val descTextView = v.findViewById<TextView>(R.id.carDescriptionText)

        if (car != null){
            descTextView.text = car.description
        }else{
            descTextView.text = R.string.car_not_found.toString()
        }
        return v
    }

}