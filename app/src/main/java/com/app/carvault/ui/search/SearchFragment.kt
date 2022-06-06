package com.app.carvault.ui.search

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.Util
import com.app.carvault.car.Car
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    private lateinit var vehicleLayout: ConstraintLayout
    private lateinit var searchBar: SearchView
    private lateinit var searchSpinner: ProgressBar

    private lateinit var carModel: TextView
    private lateinit var carVIN: TextView
    private lateinit var carImg: ImageView
    private lateinit var carOwner: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.search_fragment, container, false)
        vehicleLayout = v.findViewById(R.id.vehicleLayout)
        searchBar = v.findViewById(R.id.search_bar)
        searchSpinner = v.findViewById(R.id.searchSpinner)
        carModel = v.findViewById(R.id.carModel)
        carVIN = v.findViewById(R.id.carVIN)
        carImg = v.findViewById(R.id.carImg)
        carOwner = v.findViewById(R.id.ownerName)

        vehicleLayout.visibility = View.INVISIBLE
        searchSpinner.visibility = View.INVISIBLE

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onQueryTextSubmit(query: String): Boolean {
                vehicleLayout.visibility = View.INVISIBLE
                searchSpinner.visibility = View.VISIBLE
                searchVehicle(query)
                return false
            }
        })
        return v
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun searchVehicle(vin: String){
        lifecycleScope.launch {
            val car = withContext(Dispatchers.IO) {
                GraphqlClient.getInstance().getCarByVin(vin)
            }
            if (car != null){
                val owner = withContext(Dispatchers.IO) {
                    GraphqlClient.getInstance().getUserById(car.owner.toInt())
                }
                setupVehicle(car, owner!!)
                searchSpinner.visibility = View.INVISIBLE
                vehicleLayout.visibility = View.VISIBLE
            }else{
                showNotFoundMessage(vin)
            }
        }
    }

    private fun setupVehicle(car: Car, owner: User){
        carModel.text = car.model
        carVIN.text = car.VIN
        val bitMapImage = Util.bitmapImageFromString64(car.img?.firstOrNull(), false)
        if (bitMapImage != null) {
            carImg.setImageBitmap(bitMapImage)
        } else {
            carImg.setImageResource(R.drawable.default_cars)
        }
        carOwner.text = getString(R.string.profileName, owner.firstname ,owner.surname)
    }

    private fun showNotFoundMessage(vin: String){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Vehicle not found!")
        builder.setMessage(getString(R.string.VINNotFound, vin))
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}