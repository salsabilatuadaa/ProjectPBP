package com.android.projectpbp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.buttonRegister.setOnClickListener {
            val registerUsername = binding.editTextUsername.text.toString()
            val registerPassword = binding.editTextPassword.text.toString()
            registerDatabase(registerUsername, registerPassword)
        }

        binding.loginRedirect.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerDatabase(username: String, password : String){
        val insertRowId = databaseHelper.insertUser(username, password)
        if (insertRowId != -1L){
            Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
        }
    }
}