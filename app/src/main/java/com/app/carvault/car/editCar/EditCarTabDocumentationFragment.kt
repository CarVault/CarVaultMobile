package com.app.carvault.car.editCar

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.Util
import com.app.carvault.car.Car
import com.app.carvault.documents.Document
import com.app.carvault.documents.DocumentListAdapter
import com.app.carvault.graphql.GraphqlClient
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.time.LocalDateTime

const val PICK_PDF_FILE = 4

class EditCarTabDocumentationFragment (val car: Car?): Fragment() {

    private lateinit var galleryButton: Button
    private lateinit var previewImage: ImageView
    private lateinit var adapter: DocumentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_edit_car_tab_documentation, container, false)
        galleryButton = v.findViewById(R.id.galleryButton)

        galleryButton.setOnClickListener {
            openFile();
        }

        adapter = DocumentListAdapter { doc -> onClickOpenDoc(doc)}
        val recyclerView = v.findViewById<RecyclerView>(R.id.document_list)
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter

        GraphqlClient.getInstance().temporalCar?.let {
            adapter.submitList(GraphqlClient.getInstance().temporalCar!!.documents)
        }

        return v
    }

    private fun onClickOpenDoc(doc: Document){
        Toast.makeText(this.requireContext(), doc.name, Toast.LENGTH_SHORT).show()
    }


    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        startActivityForResult(intent, PICK_PDF_FILE)
    }

    private fun submitNewImage(){
        Toast.makeText(this.requireContext(), "Image uploaded!", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == PICK_PDF_FILE){
            if (data != null){
                data.data?.also { uri ->
                    //Toast.makeText(this.requireContext(), uri.toString(), Toast.LENGTH_SHORT).show()
                    val str = convertToBase64(uri)
                    val doc = Document(
                        id = -1,
                        name = getDocumentName(uri),
                        content = str,
                        date = LocalDateTime.now(),
                        type = "PDF"
                    )
                    GraphqlClient.getInstance().temporalCar?.documents = GraphqlClient.getInstance().temporalCar?.documents?.plus(doc)
                    adapter.submitList(GraphqlClient.getInstance().temporalCar!!.documents)
                }
            }
        }
    }


    fun convertToBase64(uri: Uri): String? {
        this.requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
            return Base64.encodeToString(inputStream.readBytes(), Base64.NO_WRAP)
        }
        return null
    }

    @SuppressLint("Range")
    fun getDocumentName(uri: Uri) : String {

        val cursor: Cursor? = this.requireContext().contentResolver.query(
            uri, null, null, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        return "Unknown"
    }

}