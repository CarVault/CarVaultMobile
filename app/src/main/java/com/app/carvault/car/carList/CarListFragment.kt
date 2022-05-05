package com.app.carvault.car.carList

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.car.CarDataSource
import com.app.carvault.car.carDetail.CarDetailActivity
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.CAR_ID
import com.app.carvault.user.UserDataSource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarListFragment : Fragment() {

    private lateinit var carDataSource: CarDataSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carDataSource = CarDataSource.getDataSource(this.requireContext())

        val v = inflater.inflate(R.layout.car_list_fragment, container, false)
        val carsAdapter = CarAdapter {car -> adapterOnClick(car)}
        val recyclerView = v.findViewById<RecyclerView>(R.id.profile_car_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = carsAdapter

        lifecycleScope.launch {
            val cars = withContext(Dispatchers.IO) {
                GraphqlClient.getInstance().getUserCars()
            }
            //Thread.sleep(5000)
            carsAdapter.submitList(cars)
        }
        return v
    }

    private fun adapterOnClick(car: Car) {
        val intent = Intent(this.requireContext(), CarDetailActivity()::class.java)
        intent.putExtra(CAR_ID, car.id)
        startActivity(intent)
    }

}