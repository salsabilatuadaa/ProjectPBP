package com.android.projectpbp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.projectpbp.databinding.ActivityMainWarungBinding

class MainWarungActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainWarungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWarungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivAddWarung.setOnClickListener {
            val intent = Intent(this, AddWarungActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.ivWarung.setOnClickListener {
            val intent = Intent(this, SelectWarung::class.java)
            startActivity(intent)
            finish()
        }
    }
}

    // Metode untuk menangani tombol atau tindakan yang memicu peralihan ke AddWarungActivity
//    fun navigateToAddWarung(view: View) {
//        val intent = Intent(this, AddWarungActivity::class.java)
//        startActivity(intent)
//    }
//
//    fun navigateToSelectWarung(view: View) {
//        val intent = Intent(this, SelectWarung::class.java)
//        startActivity(intent)
//    }
//}