package com.android.projectpbp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.Model.Menu
import com.android.projectpbp.R
import com.android.projectpbp.databinding.ActivityUpdateMenuBinding

class UpdateMenu : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateMenuBinding
    private lateinit var db : DatabaseHelper
    private var menuId : Int = -1
    private lateinit var list : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        list = findViewById(R.id.editKategoriMenu)
        val kategoriArray: Array<String> = resources.getStringArray(R.array.kategori_menu)

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            kategoriArray
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        list.adapter = adapter
//        ArrayAdapter.createFromResource(
//            this,
//            R.array.kategori_menu,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            list.adapter = adapter
//        }


        menuId = intent.getIntExtra("menu_id", -1)
        if(menuId == -1){
            finish()
            return
        }

        val menu = db.getMenuByID(menuId)
        val selectedKategori = menu.kategori

        val position = adapter.getPosition(selectedKategori)
        if (position != -1) {
            list.setSelection(position)
        }


        binding.editNamaMenu.setText(menu.namamenu)
        binding.editHargaMenu.setText(menu.harga.toString())

        binding.buttonUpdateMenu.setOnClickListener{
            val newNama = binding.editNamaMenu.text.toString()
            val newKategori = list.selectedItem.toString()
            val newHarga = binding.editHargaMenu.text.toString().toInt()
            val updateMenu = Menu(menuId, newNama, newKategori, newHarga  )
            db.updateMenu(updateMenu)
            finish()
            Toast.makeText(this, "Changed Saved", Toast.LENGTH_SHORT).show()
        }
    }
}