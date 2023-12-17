package com.android.projectpbp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SelectWarung {
    class SelectWarung : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_warung)
        }

        // Metode untuk menangani tombol atau tindakan yang memicu peralihan ke DetailWarungActivity
        fun navigateToAddWarung(view: View) {
            val intent = Intent(this, DetailWarungActivity::class.java)
            startActivity(intent)
        }
    }
}