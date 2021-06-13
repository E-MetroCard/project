package com.example.e_metro_card

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_dmrc_map.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.successful_login.*


class Loginscreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.successful_login)


        val enterStation: Button = findViewById(R.id.enterStation)
        val exitStation: Button = findViewById(R.id.exitStation)
        val checkWallet: Button = findViewById(R.id.checkWallet)

        enterStation.setOnClickListener{
            val qrint=Intent(this, qr::class.java)
            startActivity(qrint)
        }

        checkWallet.setOnClickListener {
            val walletInt=Intent(this, Wallet::class.java)
            startActivity(walletInt)
        }

        exitStation.setOnClickListener {
            val qrint=Intent(this, qr::class.java)
            startActivity(qrint)
        }
    }

}




