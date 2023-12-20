package com.android.projectpbp.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
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

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
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
            val newGambar: Bitmap? = (binding.editImageView.drawable as? BitmapDrawable)?.bitmap
            val updateMenu = Menu(menuId, newNama, newKategori, newHarga, newGambar)
            db.updateMenu(updateMenu)
            finish()
            Toast.makeText(this, "Changed Saved", Toast.LENGTH_SHORT).show()
        }
        binding.editImageView.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            try {
                // Konversi URI gambar menjadi Bitmap
                val selectedImage: Bitmap? = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                // Tampilkan gambar di ImageView
                binding.editImageView.setImageBitmap(selectedImage)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}