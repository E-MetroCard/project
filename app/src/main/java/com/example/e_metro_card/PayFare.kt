package com.example.e_metro_card

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PayFare : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_fare)

        val payFare: Button = findViewById(R.id.payFare)
        payFare.setOnClickListener{
            val payint= Intent(this, PaymentActivity::class.java)
            startActivity(payint)
        }
    }
}