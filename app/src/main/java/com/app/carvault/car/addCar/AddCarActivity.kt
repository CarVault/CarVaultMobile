package com.app.carvault.car.addCar

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.graphql.GraphqlClient
import com.google.android.material.tabs.TabItem
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

const val CAR_NAME = "name"
const val CAR_VIN = "VIN"


class AddCarActivity : AppCompatActivity() {
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

    private lateinit var vinLayout: TextView
    private lateinit var modelLayout: TextView
    private lateinit var brandLayout: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)

        findViewById<Button>(R.id.addVehicleButton).setOnClickListener {
            addCar()
        }
        brand = findViewById(R.id.brand)
        vin = findViewById(R.id.vin)
        model = findViewById(R.id.model)
        description = findViewById(R.id.description)
        kilometers = findViewById(R.id.kms)
        horsepower = findViewById(R.id.horsepower)
        year = findViewById(R.id.year)
        address = findViewById(R.id.address)
        manufacturer = findViewById(R.id.manufacturer)
        origin = findViewById(R.id.origin)
        fuel = findViewById(R.id.fuel)
        color = findViewById(R.id.color)

        vinLayout = findViewById(R.id.vinLayout)
        modelLayout = findViewById(R.id.modelLayout)
        brandLayout = findViewById(R.id.brandLayout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun addCar() {
        if (!checkMandatoryFields()){
            Toast.makeText(applicationContext, getString(R.string.toast_mandatory), Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            GraphqlClient.getInstance().addCar(
                userId = GraphqlClient.getInstance().getCurrentUser()?.id.toString(),
                vin = vin.text.toString(),
                model = model.text.toString(),
                brand = brand.text.toString(),
                description = description.text.toString(),
                kilometers = if (kilometers.text.toString().isNotBlank()) {
                    kilometers.text.toString().toInt() } else {null},
                horsepower = if (horsepower.text.toString().isNotBlank()) {
                    horsepower.text.toString().toInt() } else {null},
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

    private fun checkMandatoryFields(): Boolean {
        var check = true
        if (vin.text.isNullOrBlank() || vin.text.isNullOrEmpty()){
            vinLayout.text = "VIN*"
            vinLayout.setTextColor(Color.RED)
            check =  false
        } else {
            vinLayout.text = "VIN"
            vinLayout.setTextColor(Color.BLACK)
        }
        if (model.text.isNullOrBlank() || model.text.isNullOrEmpty()){
            modelLayout.text = getString(R.string.model_hint_asterisk)
            modelLayout.setTextColor(Color.RED)
            check = false
        } else {
            modelLayout.text = getString(R.string.model_hint)
            modelLayout.setTextColor(Color.BLACK)
        }
        if (brand.text.isNullOrBlank() || brand.text.isNullOrEmpty()){
            brandLayout.text = getString(R.string.brand_hint_asterisk)
            brandLayout.setTextColor(Color.RED)
            check = false
        } else {
            brandLayout.text = getString(R.string.brand_hint)
            brandLayout.setTextColor(Color.BLACK)
        }
        return check
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}