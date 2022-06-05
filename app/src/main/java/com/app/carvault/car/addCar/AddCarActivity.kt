package com.app.carvault.car.addCar

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Color.RED
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.graphql.GraphqlClient
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

const val CAR_NAME = "name"
const val CAR_VIN = "VIN"
const val PICK_PDF_FILE = 2

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

        findViewById<Button>(R.id.addDocButton).setOnClickListener {
            openFile();
        }

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
        var carId: Long? = null
        lifecycleScope.launch {
            carId = GraphqlClient.getInstance().addCar(
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
            if (carId!=null){
                GraphqlClient.getInstance().getCurrentUser()!!.cars.plus(
                    Car(
                        id = carId!!,
                        VIN = vin.text.toString(),
                        model = model.text.toString(),
                        brand = brand.text.toString(),
                        description = description.text.toString(),
                        kms = if (kilometers.text.toString().isNotBlank()) {
                            kilometers.text.toString().toInt() } else {0},
                        year = if (year.text.toString().isNotBlank()) {
                            year.text.toString().toInt() } else {0},
                        address = address.text.toString(),
                        manufacturer = manufacturer.text.toString(),
                        origin = origin.text.toString(),
                        fuel = fuel.text.toString(),
                        color = color.text.toString(),
                        img = listOf(),
                        owner = GraphqlClient.getInstance().getCurrentUser()!!.id
                    )
                )
            }
        }
        finish()
    }

    private fun checkMandatoryFields(): Boolean {
        var check = true
        if (vin.text.isNullOrBlank() || vin.text.isNullOrEmpty()){
            vinLayout.hint = getString(R.string.vin_hint_asterisk)
            vinLayout.setHintTextColor(0xFF0000)
            check =  false
        } else {
            vinLayout.hint = getString(R.string.vin_hint)
        }
        if (model.text.isNullOrBlank() || model.text.isNullOrEmpty()){
            modelLayout.hint = getString(R.string.model_hint_asterisk)
            modelLayout.setHintTextColor(0xFF0000)
            check = false
        } else {
            modelLayout.hint = getString(R.string.model_hint)
        }
        if (brand.text.isNullOrBlank() || brand.text.isNullOrEmpty()){
            brandLayout.hint = getString(R.string.brand_hint_asterisk)
            brandLayout.setHintTextColor(0xFF0000)
            check = false
        } else {
            brandLayout.hint = getString(R.string.brand_hint)
        }
        return check
    }

    private fun TextInputLayout.makeHintRed() {
        hint = buildSpannedString {
            color(Color.RED) { append(hint) } // Mind the space prefix.
            color(Color.RED) { append(" ") }
        }
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        startActivityForResult(intent, PICK_PDF_FILE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}