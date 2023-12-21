package com.android.projectpbp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.projectpbp.Adapter.MenuAdapter
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.databinding.ActivityListMenuBinding


class ListMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMenuBinding
    private lateinit var db : DatabaseHelper
    private lateinit var menuAdapter : MenuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        menuAdapter = MenuAdapter(db.getAllMenu(), this )

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = menuAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this, CreateMenuActivity::class.java)
            startActivity(intent)
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@ListMenuActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onResume() {
        super.onResume()
        menuAdapter.refreshData(db.getAllMenu())
    }
}