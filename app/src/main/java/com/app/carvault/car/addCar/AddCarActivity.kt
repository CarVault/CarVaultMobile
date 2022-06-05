package com.app.carvault.car.addCar

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.*
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.car.carDetail.CarTabCollectionAdapter
import com.app.carvault.graphql.GraphqlClient
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

const val CAR_NAME = "name"
const val CAR_VIN = "VIN"
const val PICK_PDF_FILE = 2
const val PHOTO_CODE = 123

class AddCarActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)


        /*findViewById<TabItem>(R.id.tabDocumentation).setOnClickListener{
            openDocPhotoTab();
        }*/


        /*lifecycleScope.launch {
            setTabAdapter()
        }*/

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tabLayout = findViewById(R.id.addCarLayout)
        viewPager = findViewById(R.id.addCarPager)
        tabLayout.addTab(tabLayout.newTab().setText("Vehicle basic info"))
        tabLayout.addTab(tabLayout.newTab().setText("Documents"))
        tabLayout.addTab(tabLayout.newTab().setText("Photos"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        setTabAdapter()
    }

    private fun setTabAdapter(){
        val tabAdapter = AddCarTabCollectionAdapter(this, supportFragmentManager, tabLayout.tabCount)
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

    private fun takePhoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null){
            startActivityForResult(intent, PHOTO_CODE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }



}