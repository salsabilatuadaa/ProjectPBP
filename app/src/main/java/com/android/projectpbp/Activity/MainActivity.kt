package com.android.projectpbp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db :DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        binding.buttonWarung.setOnClickListener{
            val intent= Intent(this, SelectWarung::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonMenu.setOnClickListener {
            val intent = Intent(this, ListMenuActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}