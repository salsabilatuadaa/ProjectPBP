package com.android.projectpbp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.projectpbp.Adapter.WarungAdapter
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.databinding.ActivityWarungBinding

class SelectWarung : AppCompatActivity() {

    private lateinit var binding: ActivityWarungBinding
    private lateinit var db : DatabaseHelper
    private lateinit var warungAdapter: WarungAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        warungAdapter = WarungAdapter(db.getAllWarung(), this)

        binding.warungRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.warungRecyclerView.adapter = warungAdapter

        binding.addButton.setOnClickListener() {
            val intent = Intent(this, AddWarungActivity::class.java)
            startActivity(intent)
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

    override fun onResume() {
        super.onResume()
        warungAdapter.refreshData(db.getAllWarung())
    }

}




