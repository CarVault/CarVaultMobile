package com.app.carvault.car.carDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.carvault.R
import com.app.carvault.car.Car


class CarTabDetailsFragment (val car: Car?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_car_tab_details, container, false)
        val descriptionText = v.findViewById<TextView>(R.id.descriptionText)
        val carBrandText = v.findViewById<TextView>(R.id.carBrandText)
        val kmsText = v.findViewById<TextView>(R.id.KmsText)
        val yearText = v.findViewById<TextView>(R.id.yearText)
        val addressText = v.findViewById<TextView>(R.id.addressText)
        val manufacturerText = v.findViewById<TextView>(R.id.manufacturerText)
        val modelText = v.findViewById<TextView>(R.id.modelText)
        val originText = v.findViewById<TextView>(R.id.originText)
        val fuelText = v.findViewById<TextView>(R.id.fuelText)
        val colorText = v.findViewById<TextView>(R.id.colorText)

        car?.let {
            descriptionText.text = car.description
            carBrandText.text = car.brand
            kmsText.text = car.kms.toString()
            yearText.text = car.year.toString()
            addressText.text = car.address
            manufacturerText.text = car.manufacturer
            modelText.text = car.model
            originText.text = car.origin
            fuelText.text = car.fuel
            colorText.text = car.color
        }
        return v
    }
}