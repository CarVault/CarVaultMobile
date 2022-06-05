package com.app.carvault.car.addCar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.carvault.R
import com.google.android.material.tabs.TabItem

class DocumentationPhotosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documentation_photos)

        /*findViewById<TabItem>(R.id.tabVehicleInfo2).setOnClickListener{
            openBasicInfoTab();
        }*/
    }

    private fun openBasicInfoTab(){
            val intent = Intent( this, AddCarActivity::class.java)
            startActivity(intent)
    }
}