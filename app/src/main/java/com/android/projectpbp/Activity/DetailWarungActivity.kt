package com.android.projectpbp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.android.projectpbp.R

class DetailWarungActivity {
    class DetailWarungActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_detail_warung)

            val callback = object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    val intent = Intent(this@DetailWarungActivity, SelectWarung::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                }
            }
            onBackPressedDispatcher.addCallback(this, callback)

        }
    }
}