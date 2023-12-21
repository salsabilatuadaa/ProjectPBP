package com.android.projectpbp.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.Model.Warung
import com.android.projectpbp.databinding.ActivityFormAddWarungBinding
import java.io.IOException
import java.io.InputStream

class AddWarungActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormAddWarungBinding
    private lateinit var db : DatabaseHelper
    private var selectedImage: Bitmap? = null
    private var selectedLogo: Bitmap? = null

    companion object {
        private const val PICK_IMAGE_REQUEST_GAMBAR = 1
        private const val PICK_IMAGE_REQUEST_LOGO = 2

    }
    fun openGallery(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, requestCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAddWarungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@AddWarungActivity, SelectWarung::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)

            }
        }
        onBackPressedDispatcher.addCallback(this, callback)


        binding.buttonAdd.setOnClickListener{
            val namawarung = binding.namaWarung.text.toString()
            val nomormeja = binding.nomorMeja.text.toString()
            val bitmapgambar : Bitmap? = (binding.warungImage.drawable as? BitmapDrawable)?.bitmap
            val bitmaplogo : Bitmap? = (binding.warungLogo.drawable as? BitmapDrawable)?.bitmap

            val warung = Warung(0, namawarung, nomormeja, bitmapgambar, bitmaplogo)
            db.insertWarung(warung)
            finish()
            Toast.makeText(this, "Warung Added", Toast.LENGTH_SHORT).show()
        }

        binding.warungImage.setOnClickListener{
            openGallery(PICK_IMAGE_REQUEST_GAMBAR)
        }
        binding.warungLogo.setOnClickListener{
            openGallery(PICK_IMAGE_REQUEST_LOGO)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE_REQUEST_GAMBAR -> handleImageResult(data, binding.warungImage)
            PICK_IMAGE_REQUEST_LOGO -> handleImageResult(data, binding.warungLogo)
        }
    }
    private fun handleImageResult(data: Intent?, imageView: ImageView) {
        if (data != null && data.data != null) {
            try {
                val inputStream: InputStream? = contentResolver.openInputStream(data.data!!)
                // Konversi InputStream menjadi Bitmap
                val selectedBitmap = BitmapFactory.decodeStream(inputStream)
                // Tampilkan gambar di ImageView yang sesuai
                imageView.setImageBitmap(selectedBitmap)

                // Simpan gambar sesuai ImageView yang dimaksud
                if (imageView == binding.warungImage) {
                    selectedImage = selectedBitmap
                } else if (imageView == binding.warungLogo) {
                    selectedLogo = selectedBitmap
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}