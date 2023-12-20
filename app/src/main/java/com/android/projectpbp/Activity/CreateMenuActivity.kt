package com.android.projectpbp.Activity

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.Model.Menu
import com.android.projectpbp.databinding.ActivityCreateMenuBinding

class CreateMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateMenuBinding
    private lateinit var db : DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        binding.buttonAdd.setOnClickListener{
            val nama = binding.namaMenu.text.toString()
//            val kategori = listOf(binding.kategoriMenu.selectedItem.toString())
            val harga = binding.hargaMenu.text.toString().toInt()
//            val bitmap: Bitmap? = (binding.imageView.drawable as? BitmapDrawable)?.bitmap

            val menu = Menu(0, nama, harga)
            db.insertMenu(menu)
            finish()
            Toast.makeText(this, "Menu Added", Toast.LENGTH_SHORT).show()
        }
    }
}