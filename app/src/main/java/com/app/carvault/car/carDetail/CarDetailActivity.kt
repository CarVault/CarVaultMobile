package com.app.carvault.car.carDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.app.carvault.R
import com.app.carvault.Util
import com.app.carvault.car.Car
import com.app.carvault.car.editCar.EditCar
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.CAR_POSITION
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class CarDetailActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private lateinit var editButton: FloatingActionButton
    private lateinit var carImages: ViewPager

    private var currentCarPosition: Int? = null
    private var currentCar : Car? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentCarPosition = bundle.getInt(CAR_POSITION)
        }

        carImages = findViewById(R.id.carImages)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.pager)
        tabLayout.addTab(tabLayout.newTab().setText("Details"))
        tabLayout.addTab(tabLayout.newTab().setText("Documents"))
        tabLayout.addTab(tabLayout.newTab().setText("Transactions"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        editButton = findViewById(R.id.editButton)
        editButton.setOnClickListener{
            editCar()
        }
    }

    override fun onStart() {
        super.onStart()
        currentCarPosition?.let{
            currentCar = GraphqlClient.getInstance().getCurrentUser()!!.cars[currentCarPosition!!.toInt()]
            setTabAdapter(currentCar)
            setupCarImages(currentCar)
        }
    }

    override fun onResume() {
        super.onResume()
        currentCarPosition?.let{
            currentCar = GraphqlClient.getInstance().getCurrentUser()!!.cars[currentCarPosition!!.toInt()]
            setTabAdapter(currentCar)
            setupCarImages(currentCar)
        }
    }

    private fun setupCarImages(car: Car?){
        car?.let {
            val carImagesAdapter = CarImagesAdapter(this, car.img?.mapNotNull { img -> Util.bitmapImageFromString64(img, false) })
            carImages.adapter = carImagesAdapter
        }
    }

    private fun editCar(){
        val intent = Intent(this, EditCar::class.java)
        intent.putExtra(CAR_POSITION, currentCarPosition)
        startActivity(intent)
    }

    private fun setTabAdapter(currentCar : Car?){
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