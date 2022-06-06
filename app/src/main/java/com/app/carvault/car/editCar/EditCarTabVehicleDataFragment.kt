package com.app.carvault.car.editCar


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.graphql.GraphqlClient
import kotlinx.coroutines.launch

class EditCarTabVehicleDataFragment (var car: Car?) : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_edit_car_tab_vehicle_data, container, false)
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

        car?.let {
            setCurrentValues(it)
        }

        brand.setOnFocusChangeListener { view, b ->
            GraphqlClient.getInstance().temporalCar?.brand = brand.text.toString()
        }
        vin.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.VIN = vin.text.toString() }
        }
        model.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.model = model.text.toString() }
        }
        description.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.description = description.text.toString() }
        }
        kilometers.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.kms = kilometers.text.toString().toIntOrNull() }
        }
        year.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.year = year.text.toString().toIntOrNull() }
        }
        address.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.address = address.text.toString() }
        }
        manufacturer.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.manufacturer = manufacturer.text.toString() }
        }
        origin.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.origin = origin.text.toString() }
        }
        fuel.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.fuel = fuel.text.toString() }
        }
        color.setOnFocusChangeListener { view, b ->
            if (!b){ GraphqlClient.getInstance().temporalCar?.color = color.text.toString() }
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
}