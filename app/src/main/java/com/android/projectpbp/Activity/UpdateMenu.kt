package com.android.projectpbp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.Model.Menu
import com.android.projectpbp.R
import com.android.projectpbp.databinding.ActivityUpdateMenuBinding

class UpdateMenu : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateMenuBinding
    private lateinit var db : DatabaseHelper
    private var menuId : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        menuId = intent.getIntExtra("menu_id", -1)
        if(menuId == -1){
            finish()
            return
        }

        val menu = db.getMenuByID(menuId)
        binding.editNamaMenu.setText(menu.namamenu)
        binding.editHargaMenu.setText(menu.harga.toString())

        binding.buttonUpdateMenu.setOnClickListener{
            val newNama = binding.editNamaMenu.text.toString()
            val newHarga = binding.editHargaMenu.text.toString().toInt()
            val updateMenu = Menu(menuId, newNama, newHarga)
            db.updateMenu(updateMenu)
            finish()
            Toast.makeText(this, "Changed Saved", Toast.LENGTH_SHORT).show()
        }
    }
}