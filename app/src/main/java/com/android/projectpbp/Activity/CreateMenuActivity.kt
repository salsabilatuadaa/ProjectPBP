package com.android.projectpbp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.projectpbp.databinding.ActivityCreateMenuBinding

class CreateMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}