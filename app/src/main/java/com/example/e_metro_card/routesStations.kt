package com.example.e_metro_card

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dmrc_map.*

class routesStations : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes_stations)

        backbtn.setOnClickListener{
            val backToMain= Intent(this, MainActivity::class.java)
            startActivity(backToMain)
        }
    }
}