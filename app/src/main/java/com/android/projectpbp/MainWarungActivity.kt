package com.android.projectpbp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainWarungActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_warung)
    }

    // Metode untuk menangani tombol atau tindakan yang memicu peralihan ke AddWarungActivity
    fun navigateToAddWarung(view: View) {
        val intent = Intent(this, AddWarungActivity::class.java)
        startActivity(intent)
    }

    fun navigateToSelectWarung(view: View) {
        val intent = Intent(this, SelectWarung::class.java)
        startActivity(intent)
    }
}