package com.app.carvault.car.editCar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.CAR_ID
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditCar : AppCompatActivity() {

    private lateinit var vin: TextInputEditText
    private lateinit var brand: TextInputEditText
    private lateinit var model: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var kilometers: TextInputEditText
    private lateinit var horsepower: TextInputEditText
    private lateinit var year: TextInputEditText
    private lateinit var address: TextInputEditText
    private lateinit var manufacturer: TextInputEditText
    private lateinit var origin: TextInputEditText
    private lateinit var fuel: TextInputEditText
    private lateinit var color: TextInputEditText

    private var currentCarId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentCarId = bundle.getLong(CAR_ID)
        }

        findViewById<Button>(R.id.doneButton).setOnClickListener {
            submitCarUpdate()
        }

        brand = findViewById(R.id.brand)
        vin = findViewById(R.id.vin)
        model = findViewById(R.id.model)
        description = findViewById(R.id.description)
        kilometers = findViewById(R.id.kms)
        year = findViewById(R.id.year)
        address = findViewById(R.id.address)
        manufacturer = findViewById(R.id.manufacturer)
        origin = findViewById(R.id.origin)
        fuel = findViewById(R.id.fuel)
        color = findViewById(R.id.color)

        lifecycleScope.launch {
            val currentCar = withContext(Dispatchers.IO) {
                GraphqlClient.getInstance().getCarById(currentCarId!!.toInt())
            }
            currentCar?.let {
                setCurrentValues(currentCar)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
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
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}