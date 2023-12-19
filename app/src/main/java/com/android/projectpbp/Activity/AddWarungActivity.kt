package com.android.projectpbp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.projectpbp.databinding.ActivityFormAddWarungBinding

class AddWarungActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormAddWarungBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAddWarungBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}