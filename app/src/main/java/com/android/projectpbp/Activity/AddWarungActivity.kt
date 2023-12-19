package com.android.projectpbp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.android.projectpbp.databinding.ActivityFormAddWarungBinding

class AddWarungActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormAddWarungBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAddWarungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@AddWarungActivity, SelectWarung::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)

            }
        }
        onBackPressedDispatcher.addCallback(this, callback)


    }
}