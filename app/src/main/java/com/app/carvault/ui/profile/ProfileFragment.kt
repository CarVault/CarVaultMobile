package com.app.carvault.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.app.carvault.R
import com.app.carvault.car.addCar.AddCarActivity
import com.app.carvault.car.addCar.CAR_NAME
import com.app.carvault.car.addCar.CAR_VIN
import com.app.carvault.car.carDetail.CarDetailActivity
import com.app.carvault.car.carList.CarAdapter
import com.app.carvault.car.Car
import com.app.carvault.car.CarDataSource
import com.app.carvault.car.carDetail.CarTabCollectionAdapter
import com.app.carvault.ui.profile.editProfile.EditProfileActivity
import com.app.carvault.ui.profile.editProfile.PROFILE_EMAIL
import com.app.carvault.ui.profile.editProfile.PROFILE_NAME
import com.app.carvault.ui.profile.editProfile.PROFILE_PHONE
import com.app.carvault.ui.profile.tabProfile.ProfileTabAdapter
import com.app.carvault.user.User
import com.app.carvault.user.UserDataSource
import com.google.android.material.tabs.TabLayout

const val CAR_ID = "car id"
const val TRANS_ID = "trans id"

class ProfileFragment : Fragment() {

    private val newCarActivityRequestCode = 1
    private val editProfileActivityRequestCode = 2

    private lateinit var userDataSource : UserDataSource
    private lateinit var carDataSource: CarDataSource

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.profile_fragment, container, false)
        userDataSource = UserDataSource.getDataSource(this.requireContext())
        carDataSource = CarDataSource.getDataSource(this.requireContext())

        // Set up user profile
        updateProfileData(v)

        // Tab Layout
        tabLayout = v.findViewById(R.id.tabLayout_profile)
        viewPager = v.findViewById(R.id.pager)

        tabLayout.addTab(tabLayout.newTab().setText("Cars"))
        tabLayout.addTab(tabLayout.newTab().setText("Transactions"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val tabAdapter = ProfileTabAdapter(this.requireContext(), this.parentFragmentManager, tabLayout.tabCount)
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


        /*carsListViewModel.carsLiveData.observe(this.viewLifecycleOwner) {
            it?.let {
                carsAdapter.submitList(it as MutableList<Car>)
            }
        }
         */

        // Fab -> adding new cars
        val fab: View = v.findViewById(R.id.floatingAddCarButton)
        fab.setOnClickListener {
            fabOnClick()
            //Toast.makeText(this.context, "Add new car!", Toast.LENGTH_SHORT).show()
        }

        // Edit profile button
        val editButton = v.findViewById<Button>(R.id.button_edit_profile)
        editButton.setOnClickListener {
            //Toast.makeText(this.context, "Edit button!", Toast.LENGTH_SHORT).show()
            editButtonOnClick()
        }
        return v
    }


    private fun updateProfileData(v: View){
        val user: User = userDataSource.getCurrentUser()

        val profileName = v.findViewById<TextView>(R.id.profile_name)
        val profileId = v.findViewById<TextView>(R.id.profile_id)
        val profileEmail = v.findViewById<TextView>(R.id.profile_email)
        val profilePhone = v.findViewById<TextView>(R.id.profile_phone)
        val profileImg = v.findViewById<ImageView>(R.id.profile_img)

        profileName.text = user.name
        profileId.text = user.id.toString()
        profileEmail.text = user.email
        profilePhone.text = user.phone

        if (user.profile_img != ""){
            profileImg.setImageURI(Uri.parse(user.profile_img))
        }else{
            profileImg.setImageResource(R.drawable.ic_baseline_person_24)
        }
    }

    private fun fabOnClick() {
        val intent = Intent(this.context, AddCarActivity::class.java)
        startActivityForResult(intent, newCarActivityRequestCode)
    }

    private fun editButtonOnClick(){
        val intent = Intent(this.context, EditProfileActivity::class.java)
        startActivityForResult(intent, editProfileActivityRequestCode)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                1  -> intentData?.let { data ->
                    val carName = data.getStringExtra(CAR_NAME)
                    val carVIN = data.getStringExtra(CAR_VIN)
                    //carsListViewModel.insertCar(carName, carVIN)
                }
                2 -> intentData?.let { data ->
                    val profileName = data.getStringExtra(PROFILE_NAME)
                    val profileEmail = data.getStringExtra(PROFILE_EMAIL)
                    val profilePhone = data.getStringExtra(PROFILE_PHONE)
                    userDataSource.updateUser(profileName, profileEmail, profilePhone)
                }
            }
        }

    }


}