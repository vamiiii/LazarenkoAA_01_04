package com.example.Lazarenko_01_04

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.gorovoyvs_01_04.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var button: AppCompatButton
    private lateinit var SharedPreferences: SharedPreferences


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        login = findViewById(R.id.login)
        password = findViewById(R.id.password)
        button = findViewById(R.id.enter)

        SharedPreferences = getSharedPreferences("file", MODE_PRIVATE)

        val resetLogin = intent.getBooleanExtra("reset_login", false)
        if (resetLogin) {
            clearData()
        } else {
            loadData()
        }

        loadData()


        button.setOnClickListener{
            if (!login.text.isNullOrEmpty() || !password.text.isNullOrEmpty())
            {
                saveData()
                Snackbar.make(button, "Данные сохранены!", Snackbar.LENGTH_LONG).show()

                val intent = Intent(this, CreditCalculator::class.java)
                startActivity(intent)
            }
            else
            {
                Snackbar.make(button, "Пожалуйста, заполните все поля!", Snackbar.LENGTH_LONG).show()
            }
        }

    }

    private fun saveData() {
        val editor = SharedPreferences.edit()
        editor.putString("LOGIN", login.text.toString())
        editor.putString("PASSWORD", password.text.toString())
        editor.apply()
    }

    private fun loadData() {
        login.setText(SharedPreferences.getString("LOGIN", ""))
        password.setText(SharedPreferences.getString("PASSWORD", ""))
    }

    private fun clearData() {
        val editor = SharedPreferences.edit()
        editor.clear()
        editor.apply()

        login.setText("")
        password.setText("")
    }
}