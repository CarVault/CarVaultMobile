package com.app.carvault.car.editCar

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.app.carvault.R
import com.app.carvault.ui.profile.editProfile.PICK_IMG_FILE


class EditCarTabPhotosFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val editCar = EditCar()
        val v = inflater.inflate(R.layout.fragment_edit_car_tab_phots, container, false)
        v.findViewById<ImageButton>(R.id.editCarPhotoimageButton).setOnClickListener {
            editCar.dispatchTakePictureIntent();
        }
        v.findViewById<ImageButton>(R.id.editCarAddPhotoGallery).setOnClickListener {
            pickPhotoFromGallery();
        }
        return v
    }

    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMG_FILE )
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageView: ImageView = findViewById<ImageView>(R.id.image)
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }*/



}