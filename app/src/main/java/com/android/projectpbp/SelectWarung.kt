package com.android.projectpbp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.projectpbp.databinding.ActivityWarungBinding

class SelectWarung : AppCompatActivity() {

    private lateinit var binding: ActivityWarungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarungBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_warung)

        binding.ivAddWarung.setOnClickListener{
            val intent = Intent(this, AddWarungActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    // Metode untuk menangani tombol atau tindakan yang memicu peralihan ke DetailWarungActivity
//    fun navigateToAddWarung(view: View) {
//        val intent = Intent(this, DetailWarungActivity::class.java)
//        startActivity(intent)
//    }
}