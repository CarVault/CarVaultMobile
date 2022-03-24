package com.app.carvault.ui.profile.carRecyclerView.carDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.app.carvault.R
import com.app.carvault.ui.profile.CAR_ID

class CarDetailActivity : AppCompatActivity() {

    private val carDetailViewModel by viewModels<CarDetailViewModel> {
        CarDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)

        var currentCarId: Long? = null

        /* Connect variables to UI elements. */
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
            /*
            removeCarButton.setOnClickListener {
                if (currentCar != null) {
                    carDetailViewModel.removeCar(currentCar)
                }
                finish()
            }
             */
        }
    }
}