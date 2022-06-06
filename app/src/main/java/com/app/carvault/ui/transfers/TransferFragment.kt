package com.app.carvault.ui.transfers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.Util
import com.app.carvault.car.Car
import com.app.carvault.car.carDetail.CarDetailActivity
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.CAR_POSITION
import com.app.carvault.user.User
import kotlinx.coroutines.launch


class TransferFragment : Fragment(),  AdapterView.OnItemSelectedListener {

    private lateinit var detailsButton: Button
    private lateinit var spinner: Spinner
    private lateinit var selectedCarView: TextView
    private lateinit var searchUserBar: SearchView
    private lateinit var selectedCar: Car
    private var foundUser: User? = null

    private lateinit var completeName: TextView
    private lateinit var usernameText: TextView
    private lateinit var email: TextView
    private lateinit var phone: TextView
    private lateinit var userImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val v = inflater.inflate(R.layout.fragment_transfer, container, false)

        detailsButton = v.findViewById(R.id.details)
        spinner = v.findViewById(R.id.spinner)
        selectedCarView = v.findViewById(R.id.selectedCar)
        searchUserBar = v.findViewById(R.id.searchUser)

        completeName = v.findViewById(R.id.name)
        usernameText = v.findViewById(R.id.username)
        email = v.findViewById(R.id.email)
        phone = v.findViewById(R.id.phone)
        userImage = v.findViewById(R.id.userImage)

        userImage.visibility = View.INVISIBLE

        val carModels = GraphqlClient.getInstance().getCurrentUser()!!.cars.map { it.model }
        setupSpinner(carModels)

        detailsButton.setOnClickListener {
           onDetailClick(selectedCar)
        }

        searchUserBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchUser(query)
                return false
            }
        })
        // Listener on transfer button
        val transferButton: View = v.findViewById(R.id.transferButton)
        transferButton.setOnClickListener {
            transferVehicle()
        }
        return v
    }

    private fun setupSpinner(carModels: List<String>){
        val aa = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item,carModels)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = aa
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        selectedCar = GraphqlClient.getInstance().getCurrentUser()!!.cars[position]
        selectedCarView.text = getString(
            R.string.selected_car,
            GraphqlClient.getInstance().getCurrentUser()!!.cars[position].model
        )
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private fun onDetailClick(car: Car) {
        val intent = Intent(this.requireContext(), CarDetailActivity()::class.java)
        val index =  GraphqlClient.getInstance().getCurrentUser()!!.cars.indexOf(car)
        intent.putExtra(CAR_POSITION, index)
        startActivity(intent)
    }

    private fun searchUser(username: String){
        lifecycleScope.launch {
            foundUser = GraphqlClient.getInstance().getUserByUsername(username)
            if (foundUser != null){
                setupUserInfo(foundUser!!)
            }else{
                showNotFoundMessage(username)
            }
        }
    }

    private fun setupUserInfo(user: User){
        completeName.text = getString(R.string.profileName, user.firstname, user.surname)
        usernameText.text = getString(R.string.user_username, user.username)
        email.text = getString(R.string.user_email, user.email)
        phone.text = getString(R.string.user_phone, user.phone)
        val bitMapImage = Util.bitmapImageFromString64(user.profilePicture, true)
        if (bitMapImage != null){
            userImage.setImageBitmap(bitMapImage)
            userImage.visibility = View.VISIBLE
        }
    }

    private fun showNotFoundMessage(username: String){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("User not found!")
        builder.setMessage(getString(R.string.UsernameNotFound, username))
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun transferVehicle(){
        val builder = AlertDialog.Builder(this.requireContext())
        if (foundUser==null){
            Toast.makeText(this.requireContext(), "Please, select the user you want to transfer the vehicle to", Toast.LENGTH_SHORT).show()
            return
        }

        builder.setMessage(getString(R.string.transfer_confirm, selectedCar.model, foundUser!!.firstname, foundUser!!.surname))
            .setCancelable(false)
            .setNegativeButton("No") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            .setPositiveButton("Yes") { _, _ ->
                var response: Long? = null
                lifecycleScope.launch {
                    foundUser?.let {
                        response = GraphqlClient.getInstance().transferCar(
                            userId = foundUser!!.id.toString(),
                            carId = selectedCar.id.toString()
                        )
                    }
                }
                if (response!=null){
                    Toast.makeText(this.requireContext(), "Vehicle successfully transferred!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this.requireContext(), "Could not transfer the vehicle", Toast.LENGTH_SHORT).show()
                }
            }

        val alert = builder.create()
        alert.show()
    }

}