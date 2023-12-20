package com.android.projectpbp.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.Model.Menu
import com.android.projectpbp.R
import com.android.projectpbp.databinding.ActivityCreateMenuBinding

class CreateMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateMenuBinding
    private lateinit var db : DatabaseHelper
    private lateinit var list : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        list = findViewById(R.id.kategoriMenu)
        ArrayAdapter.createFromResource(
            this,
            R.array.kategori_menu,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            list.adapter = adapter
        }

        db = DatabaseHelper(this)

        binding.buttonAdd.setOnClickListener{
            val nama = binding.namaMenu.text.toString()
            val kategori = list.selectedItem.toString()
            val harga = binding.hargaMenu.text.toString().toInt()
//            val bitmap: Bitmap? = (binding.imageView.drawable as? BitmapDrawable)?.bitmap

            val menu = Menu(0, nama,kategori, harga)
            db.insertMenu(menu)
            finish()
            Toast.makeText(this, "Menu Added", Toast.LENGTH_SHORT).show()
        }
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@CreateMenuActivity, ListMenuActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }
}