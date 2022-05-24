package com.app.carvault.car.carDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.car.CarDataSource
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.CAR_ID
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*

class CarDetailActivity : AppCompatActivity() {

    private lateinit var carDataSource : CarDataSource

    private lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)
        val bundle: Bundle? = intent.extras
        var currentCarId: Long? = null
        if (bundle != null) {
            currentCarId = bundle.getLong(CAR_ID)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        lifecycleScope.launch {
            val currentCar = withContext(Dispatchers.IO) {
                GraphqlClient.getInstance().getCarById(currentCarId!!.toInt())
            }
            setTabAdapter(currentCar)
        }

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.pager)
        tabLayout.addTab(tabLayout.newTab().setText("Description"))
        tabLayout.addTab(tabLayout.newTab().setText("Details"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        /* Connect variables to UI elements.
        val carName: TextView = findViewById(R.id.carDetailName)
        val carVIN: TextView = findViewById(R.id.carDetailVIN)
        val carImage: ImageView = findViewById(R.id.carDetailImg)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentCarId = bundle.getLong(CAR_ID)
        }

        currentCarId?.let {
            val currentCar = carDetailViewModel.getCarForId(it)
            carName.text = currentCar?.name
            carVIN.text = currentCar?.VIN
            if (currentCar?.img == null) {
                carImage.setImageResource(R.drawable.default_cars)
            } else {
                carImage.setImageResource(currentCar.img!!)
            }

            actionBar?.title = currentCar?.name
            supportActionBar?.title = currentCar?.name
            /* Para borrar el coche de la lista
            removeCarButton.setOnClickListener {
                if (currentCar != null) {
                    carDetailViewModel.removeCar(currentCar)
                }
                finish()
            } */
        }

         */
    }

    fun setTabAdapter(currentCar : Car?){
        val tabAdapter = CarTabCollectionAdapter(this, supportFragmentManager, tabLayout.tabCount, currentCar)
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

}