package com.app.carvault.car.editCar

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.Util
import com.app.carvault.car.Car
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.profile.editProfile.PICK_IMG_FILE
import kotlinx.coroutines.launch


const val PICK_IMG_GALLERY = 11

class EditCarTabPhotosFragment (val car: Car?) : Fragment() {

    private lateinit var cameraButton: ImageButton
    private lateinit var galleryButton: Button
    private lateinit var previewImage: ImageView
    private lateinit var removeButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_edit_car_tab_phots, container, false)
        cameraButton = v.findViewById(R.id.cameraButton)
        galleryButton = v.findViewById(R.id.galleryButton)
        previewImage = v.findViewById(R.id.previewImage)
        removeButton = v.findViewById(R.id.removeImages)

       cameraButton.setOnClickListener {
            dispatchTakePictureIntent();
        }
        galleryButton.setOnClickListener {
            pickPhotoFromGallery();
        }
        removeButton.setOnClickListener{
            removeAllImages()
        }

        return v
    }

    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMG_GALLERY )
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK){
            val bitmap = when (requestCode){
                PICK_IMG_GALLERY -> data?.let {MediaStore.Images.Media.getBitmap(this.requireContext().contentResolver, data.data)}
                REQUEST_IMAGE_CAPTURE -> data?.let { it.extras?.get("data") as Bitmap }
                else -> null
            }
            bitmap?.let {
                previewImage.setImageBitmap(bitmap)
                val str64 = Util.string64FromBitmapImage(bitmap)
                str64?.let {
                    GraphqlClient.getInstance().temporalCar?.img = GraphqlClient.getInstance().temporalCar?.img?.plus(str64)
                }
                if (str64==null){
                    Toast.makeText(this.requireContext(), "invalid image", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun removeAllImages(){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setMessage("Are you sure you want to delete ALL photos of this vehicle?")
            .setCancelable(false)
            .setNegativeButton("No") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            .setPositiveButton("Yes") { _, _ ->
                var response: Long? = null
                lifecycleScope.launch {
                    response = GraphqlClient.getInstance().deleteImages(
                        carId = car!!.id.toString(),
                    )
                }
                if (response!=null){
                    Toast.makeText(this.requireContext(), "Images deleted", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this.requireContext(), "Could not delete the images", Toast.LENGTH_SHORT).show()
                }
            }
        val alert = builder.create()
        alert.show()
    }



}