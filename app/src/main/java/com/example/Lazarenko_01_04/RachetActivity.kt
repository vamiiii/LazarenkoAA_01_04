package com.example.Lazarenko_01_04

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.gorovoyvs_01_04.R

class RachetActivity : AppCompatActivity() {



    private lateinit var sumCreditEditText: EditText
    private lateinit var termEditText: EditText
    private lateinit var resultEditText: EditText
    private lateinit var registerButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rachet)

        sumCreditEditText = findViewById(R.id.sum_credit)
        termEditText = findViewById(R.id.srok_credit2)
        resultEditText = findViewById(R.id.pay_per_month2)
        registerButton = findViewById(R.id.back_to_register)

        val sumCredit = intent.getStringExtra("sum_credit") ?: ""
        val srokCredit = intent.getStringExtra("srok_credit") ?: ""
        val payPerMonth = intent.getStringExtra("pay_per_month") ?: ""

        sumCreditEditText.setText(sumCredit)
        termEditText.setText(srokCredit)
        resultEditText.setText(payPerMonth)

    }

    fun back_to_main(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("reset_login", true)
        startActivity(intent)
    }
}