package com.example.Lazarenko_01_04

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import android.widget.*
import com.example.gorovoyvs_01_04.R

class CreditCalculator : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private lateinit var amountTextView: TextView
    private lateinit var termEditText: EditText
    private lateinit var calculateButton: AppCompatButton
    private lateinit var resultEditText: EditText

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_credit_calculator)

        seekBar = findViewById(R.id.seekBar)
        amountTextView = findViewById(R.id.textViewAmount)
        termEditText = findViewById(R.id.srok_credit)
        calculateButton = findViewById(R.id.enter)
        resultEditText = findViewById(R.id.pay_per_month)

        seekBar.min = 30000
        seekBar.max = 100000

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                amountTextView.text = "$progress ₽"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        calculateButton.setOnClickListener {
            calculateMonthlyPayment()
        }

    }

    private fun calculateMonthlyPayment() {
        val amount = seekBar.progress
        val termText = termEditText.text.toString()

        if (termText.isEmpty()) {
            Toast.makeText(this, "Введите срок кредита!", Toast.LENGTH_SHORT).show()
            return
        }

        val term = termText.toInt()

        if (term <= 0) {
            Toast.makeText(this, "Срок должен быть больше 0!", Toast.LENGTH_SHORT).show()
            return
        }

        val monthlyPayment = when {
            term <= 12 -> {
                amount / term + amount * 0.059
            }
            term in 13..24 -> {
                val s1 = amount / 12 + amount * 0.059
                s1 + amount / term + (amount - s1 * 12) * 0.051
            }
            else -> {
                val s1 = amount / 12 + amount * 0.059
                val s2 = s1 + amount / term + (amount - s1 * 12) * 0.051
                s2 + amount / term + (amount - s2 * term) * 0.042
            }
        }

        resultEditText.setText("%.2f тыс. ₽".format(monthlyPayment / 1000))

        Handler(Looper.getMainLooper()).postDelayed({
            goToRachetActivity(amount, term, monthlyPayment)
        }, 1000)
    }

    fun back_to_register(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun goToRachetActivity(amount: Int, term: Int, monthlyPayment: Double) {
        val intent = Intent(this, RachetActivity::class.java).apply {
            putExtra("sum_credit", amount.toString())
            putExtra("srok_credit", term.toString())
            putExtra("pay_per_month", "%.2f тыс. ₽".format(monthlyPayment / 1000))
        }
        startActivity(intent)
    }
}