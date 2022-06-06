package com.app.carvault.car.editCar


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.CAR_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditCarTabVehicleDataFragment : Fragment() {

    private lateinit var vin: EditText
    private lateinit var brand: EditText
    private lateinit var model: EditText
    private lateinit var description: EditText
    private lateinit var kilometers: EditText
    private lateinit var horsepower: EditText
    private lateinit var year: EditText
    private lateinit var address: EditText
    private lateinit var manufacturer: EditText
    private lateinit var origin: EditText
    private lateinit var fuel: EditText
    private lateinit var color: EditText

    private var currentCarId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val intent = Intent()
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentCarId = bundle.getLong(CAR_ID)
        }
        val editCar = EditCar()
        val v = inflater.inflate(R.layout.fragment_edit_car_tab_vehicle_data, container, false)

        v.findViewById<Button>(R.id.updateVehicleButton).setOnClickListener {
            submitCarUpdate()
        }
        brand = v.findViewById(R.id.brand)
        vin = v.findViewById(R.id.vin)
        model = v.findViewById(R.id.model)
        description = v.findViewById(R.id.description)
        kilometers = v.findViewById(R.id.kms)
        year = v.findViewById(R.id.year)
        address = v.findViewById(R.id.address)
        manufacturer = v.findViewById(R.id.manufacturer)
        origin = v.findViewById(R.id.origin)
        fuel = v.findViewById(R.id.fuel)
        color = v.findViewById(R.id.color)

        lifecycleScope.launch {
            val currentCar = withContext(Dispatchers.IO) {
                GraphqlClient.getInstance().getCarById(currentCarId!!.toInt())
            }
            currentCar?.let {
                setCurrentValues(currentCar)
            }
        }
        return v
    }

    private fun setCurrentValues(car: Car){
        brand.setText(car.brand)
        vin.setText(car.VIN)
        model.setText(car.model)
        description.setText(car.description)
        kilometers.setText(car.kms.toString())
        year.setText(car.year.toString())
        address.setText(car.address)
        manufacturer.setText(car.manufacturer)
        origin.setText(car.origin)
        fuel.setText(car.fuel)
        color.setText(car.color)
    }

    private fun submitCarUpdate(){
        lifecycleScope.launch {
            GraphqlClient.getInstance().updateCar(
                carId = currentCarId.toString(),
                vin = vin.text.toString(),
                brand = brand.text.toString(),
                model = model.text.toString(),
                description = description.text.toString(),
                kilometers = if (kilometers.text.toString().isNotBlank()) {
                    kilometers.text.toString().toInt() } else {null},
                horsepower = 0,
                year = if (year.text.toString().isNotBlank()) {
                    year.text.toString().toInt() } else {null},
                address = address.text.toString(),
                manufacturer = manufacturer.text.toString(),
                origin = origin.text.toString(),
                fuel = fuel.text.toString(),
                color = color.text.toString()
            )
        }

    }



}