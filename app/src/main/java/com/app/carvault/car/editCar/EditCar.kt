package com.app.carvault.car.editCar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.CAR_POSITION
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

const val REQUEST_IMAGE_CAPTURE = 2

class EditCar : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var confirmButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var deleteCar: Button

    private var currentCarPosition: Int? = null
    private var currentCar : Car? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentCarPosition = bundle.getInt(CAR_POSITION)
        }

        currentCarPosition?.let{
            currentCar = GraphqlClient.getInstance().getCurrentUser()!!.cars[currentCarPosition!!.toInt()]
            GraphqlClient.getInstance().temporalCar = currentCar
        }

        progressBar = findViewById(R.id.progress_horizontal)

        confirmButton = findViewById(R.id.confirm_button)
        confirmButton.setOnClickListener{
            askForConfirmation()
        }

        deleteCar = findViewById(R.id.deleteCar)
        deleteCar.setOnClickListener{
            deleteCar()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tabLayout = findViewById(R.id.editCarLayout)
        viewPager = findViewById(R.id.editCarPager)
        tabLayout.addTab(tabLayout.newTab().setText("Vehicle basic info"))
        tabLayout.addTab(tabLayout.newTab().setText("Documents"))
        tabLayout.addTab(tabLayout.newTab().setText("Photos"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        setTabAdapter()
    }


    override fun onStart() {
        super.onStart()
        progressBar.isIndeterminate = true
        progressBar.visibility = View.GONE

    }

    private fun setTabAdapter(){
        val tabAdapter = EditCarTabCollectionAdapter(this, supportFragmentManager, tabLayout.tabCount, currentCar)
        viewPager.adapter = tabAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun submitChanges(){
        GraphqlClient.getInstance().temporalCar?.let { car ->
            lifecycleScope.launch {
                progressBar.visibility = View.VISIBLE
                GraphqlClient.getInstance().updateCar(
                    carId = car.id.toString(),
                    vin = car.VIN,
                    brand = car.brand,
                    model = car.model,
                    description = car.description,
                    kilometers = car.kms,
                    horsepower = 0,
                    year = car.year,
                    address = car.address,
                    manufacturer = car.manufacturer,
                    origin = car.origin,
                    fuel = car.fuel,
                    color = car.color,
                    images = car.img
                )
                GraphqlClient.getInstance().updateDocuments(
                    carId = car.id.toString(),
                    documents = car.documents!!
                )
                GraphqlClient.getInstance().temporalCar = null
                finish()
            }
        }
    }

    private fun askForConfirmation(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to save the changes?")
            .setCancelable(false)
            .setNegativeButton("No") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            .setPositiveButton("Yes") { _, _ ->
                submitChanges()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun deleteCar(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete the vehicle?")
            .setCancelable(false)
            .setNegativeButton("No") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            .setPositiveButton("Yes") { _, _ ->
                progressBar.visibility = View.VISIBLE
                lifecycleScope.launch {
                    GraphqlClient.getInstance().deleteCar(currentCar!!.id.toString())
                    GraphqlClient.getInstance().getCurrentUser()?.cars =
                        GraphqlClient.getInstance().getCurrentUser()?.cars?.let {
                            it.filterIndexed { index, _ -> index == currentCarPosition }
                        }!!
                }
                finish()
            }
        val alert = builder.create()
        alert.show()
    }
}