package com.app.carvault.car.editCar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
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