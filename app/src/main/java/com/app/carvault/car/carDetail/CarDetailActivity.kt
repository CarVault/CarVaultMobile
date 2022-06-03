package com.app.carvault.car.carDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.car.addCar.AddCarActivity
import com.app.carvault.car.editCar.EditCar
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.CAR_ID
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*

class CarDetailActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private lateinit var editButton: Button
    private var currentCarId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentCarId = bundle.getLong(CAR_ID)
        }

        lifecycleScope.launch {
            val currentCar = withContext(Dispatchers.IO) {
                GraphqlClient.getInstance().getCarById(currentCarId!!.toInt())
            }
            setTabAdapter(currentCar)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        editButton = findViewById(R.id.editButton)
        editButton.setOnClickListener{
            editCar()
        }

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.pager)
        tabLayout.addTab(tabLayout.newTab().setText("Description"))
        tabLayout.addTab(tabLayout.newTab().setText("Details"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

    }

    private fun editCar(){
        val intent = Intent(this, EditCar::class.java)
        intent.putExtra(CAR_ID, currentCarId)
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