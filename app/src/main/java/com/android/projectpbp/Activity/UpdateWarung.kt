package com.android.projectpbp.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.Model.Menu
import com.android.projectpbp.Model.Warung
import com.android.projectpbp.R
import com.android.projectpbp.databinding.ActivityUpdateWarungBinding

class UpdateWarung : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateWarungBinding
    private lateinit var db : DatabaseHelper
    private var warungId : Int = -1

    companion object {
        const val PICK_IMAGE_REQUEST_GAMBAR = 1
        const val PICK_IMAGE_REQUEST_LOGO = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateWarungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        warungId = intent.getIntExtra("warung_id", -1)
        if(warungId == -1){
            finish()
            return
        }

        val warung = db.getWarungByID(warungId)

        binding.editWarungImage.setImageBitmap(warung.gambarwarung)
        binding.editNamaWarung.setText(warung.namawarung)
        binding.editNomorMeja.setText(warung.nomormeja)
        binding.editWarungLogo.setImageBitmap(warung.logowarung)

        binding.buttonUpdateWarung.setOnClickListener{
            val newNama = binding.editNamaWarung.text.toString()
            val newNomorMeja = binding.editNomorMeja.text.toString()
            val newGambar: Bitmap? = (binding.editWarungImage.drawable as? BitmapDrawable)?.bitmap
            val newLogo: Bitmap? = (binding.editWarungLogo.drawable as? BitmapDrawable)?.bitmap

            val resizedGambar = newGambar?.let { resizeImage(it, 250, 250) }
            val resizedLogo = newLogo?.let { resizeImage(it, 250, 250) }

            val updateWarung = Warung(warungId, newNama, newNomorMeja, resizedGambar, resizedLogo)
            db.UpdateWarung(updateWarung)
            finish()
            Toast.makeText(this, "Changed Saved", Toast.LENGTH_SHORT).show()
        }
        binding.editWarungImage.setOnClickListener {
            openGallery(PICK_IMAGE_REQUEST_GAMBAR)
        }
        binding.editWarungLogo.setOnClickListener {
            openGallery(PICK_IMAGE_REQUEST_LOGO)
        }
    }
    private fun openGallery(requestCode: Int) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, requestCode)
    }

    private fun resizeImage(originalBitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val uri = data.data
            try {
                val selectedImage: Bitmap =
                    MediaStore.Images.Media.getBitmap(contentResolver, uri)

                val resizedImage = resizeImage(selectedImage, 250, 250)
                // Tampilkan gambar di ImageView sesuai dengan requestCode
                when (requestCode) {
                    PICK_IMAGE_REQUEST_GAMBAR -> binding.editWarungImage.setImageBitmap(resizedImage)
                    PICK_IMAGE_REQUEST_LOGO -> binding.editWarungLogo.setImageBitmap(resizedImage)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}