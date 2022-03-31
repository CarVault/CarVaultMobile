package com.app.carvault.car.carDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import com.app.carvault.R
import com.google.android.material.tabs.TabLayout

class CarDetailActivity : AppCompatActivity() {

    /*
    private val carDetailViewModel by viewModels<CarDetailViewModel> {
        CarDetailViewModelFactory(this)
    }
     */

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)
        var currentCarId: Long? = null

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.pager)

        tabLayout.addTab(tabLayout.newTab().setText("Description"))
        tabLayout.addTab(tabLayout.newTab().setText("Details"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = CarTabCollectionAdapter(this, supportFragmentManager, tabLayout.tabCount)
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
}