package com.example.e_metro_card

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_wallet.*

class Wallet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        val addMoney: Button = findViewById(R.id.addMoney)

        addMoney.setOnClickListener{
            val payint= Intent(this, PaymentActivity::class.java)
            startActivity(payint)
        }
    }
}