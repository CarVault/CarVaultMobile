package com.app.carvault.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.car.addCar.AddCarActivity
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.editProfile.EditProfileActivity
import com.app.carvault.user.User
import com.app.carvault.Util
import com.app.carvault.car.Car
import com.app.carvault.car.carDetail.CarDetailActivity
import com.app.carvault.car.carList.CarAdapter


const val CAR_POSITION = "car position"

class ProfileFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var carsAdapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.profile_fragment, container, false)

        // Set up user profile
        recyclerView = v.findViewById(R.id.profile_car_list)

        //updateProfileData(v, GraphqlClient.getInstance().getCurrentUser())
        carsAdapter = CarAdapter {car -> adapterOnClick(car)}
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = carsAdapter

        // Tab Layout
        //setupCarList()

        // Fab -> adding new cars
        val fab: View = v.findViewById(R.id.floatingAddCarButton)
        fab.setOnClickListener {
            fabOnClick()
        }
        // Edit profile button
        val editButton = v.findViewById<Button>(R.id.button_edit_profile)
        editButton.setOnClickListener {
            editButtonOnClick()
        }
        return v
    }

    override fun onStart() {
        super.onStart()
        updateProfileData(this.requireView(), GraphqlClient.getInstance().getCurrentUser())
        setupCarList()
    }

    override fun onResume() {
        super.onResume()
        updateProfileData(this.requireView(), GraphqlClient.getInstance().getCurrentUser())
        setupCarList()
    }

    private fun setupCarList(){
        GraphqlClient.getInstance().getCurrentUser()?.let {
            carsAdapter.submitList(it.cars)
        }
    }

    private fun adapterOnClick(car: Car) {
        val intent = Intent(this.requireContext(), CarDetailActivity()::class.java)
        val index =  GraphqlClient.getInstance().getCurrentUser()!!.cars.indexOf(car)
        intent.putExtra(CAR_POSITION, index)
        startActivity(intent)
    }

    private fun updateProfileData(v: View, user: User?){
        user?.let {
            val profileName = v.findViewById<TextView>(R.id.profile_name)
            val username = v.findViewById<TextView>(R.id.username)
            val profileEmail = v.findViewById<TextView>(R.id.profile_email)
            val profilePhone = v.findViewById<TextView>(R.id.profile_phone)
            val profileImg = v.findViewById<ImageView>(R.id.profile_img)

            profileName.text = getString(R.string.profileName, user.firstname ,user.surname )
            username.text = user.username
            profileEmail.text = user.email
            profilePhone.text = user.phone
            val bitMapImage = Util.bitmapImageFromString64(user.profilePicture, false)
            if (bitMapImage != null){
                profileImg.setImageBitmap(bitMapImage)
            } else {
                profileImg.setImageResource(R.drawable.ic_baseline_person_24)
            }
        }
    }

    private fun fabOnClick() {
        val intent = Intent(this.context, AddCarActivity::class.java)
        startActivity(intent)
    }

    private fun editButtonOnClick(){
        val intent = Intent(this.context, EditProfileActivity::class.java)
        startActivity(intent)
    }



}