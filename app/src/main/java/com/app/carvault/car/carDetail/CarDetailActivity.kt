package com.app.carvault.car.carDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.car.CarDataSource
import com.app.carvault.user.UserDataSource
import com.google.android.material.tabs.TabLayout

class CarDetailActivity : AppCompatActivity() {

    /*
    private val carDetailViewModel by viewModels<CarDetailViewModel> {
        CarDetailViewModelFactory(this)
    }
     */
    private lateinit var carDataSource : CarDataSource

    private lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)
        var currentCarId: Long = 1
        carDataSource = CarDataSource.getDataSource(this)
        val currentCar = carDataSource.getCarForId(currentCarId)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.pager)

        tabLayout.addTab(tabLayout.newTab().setText("Description"))
        tabLayout.addTab(tabLayout.newTab().setText("Details"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val tabAdapter = CarTabCollectionAdapter(this, supportFragmentManager, tabLayout.tabCount, currentCar!!)
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