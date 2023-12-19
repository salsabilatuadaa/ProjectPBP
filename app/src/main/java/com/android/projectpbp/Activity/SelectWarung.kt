package com.android.projectpbp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.android.projectpbp.R
import com.android.projectpbp.databinding.ActivityWarungBinding

class SelectWarung : AppCompatActivity() {

    private lateinit var binding: ActivityWarungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener() {
            val intent = Intent(this, AddWarungActivity::class.java)
            startActivity(intent)
            finish()
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@SelectWarung, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)

            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }


}




    // Metode untuk menangani tombol atau tindakan yang memicu peralihan ke DetailWarungActivity
//    fun navigateToAddWarung(view: View) {
//        val intent = Intent(this, DetailWarungActivity::class.java)
//        startActivity(intent)
//    }
