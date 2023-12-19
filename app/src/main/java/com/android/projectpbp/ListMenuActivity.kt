package com.android.projectpbp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.projectpbp.databinding.ActivityListMenuBinding


class ListMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener{
            val intent = Intent(this, CreateMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}