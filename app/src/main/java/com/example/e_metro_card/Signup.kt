package com.example.e_metro_card


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*


class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        btn_signup.setOnClickListener {
            saveHero()
            val ssignup=Intent(this, PhoneNumber::class.java)
            startActivity(ssignup)
        }
    }

    private fun saveHero() {
        val name = full_Name.text.toString().trim()
        val phoneno = phoneNumber.text.toString().trim()
        val emailid = emailId.text.toString().trim()


        if (phoneno.isEmpty() && phoneno.length <10) {
            phoneNumber.error = "Please enter phone number"
            return
        }

        if (emailid.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this.toString()).matches()) {

            emailId.error="Please enter a valid email id"
            return
        }
       val ref = FirebaseDatabase.getInstance().getReference("heroes")
        val heroid=ref.push().key
        val hero=Hero(heroid,name,phoneno,emailid)
        ref.child(heroid.toString()).setValue(hero).addOnCompleteListener{
            Toast.makeText(applicationContext,"Hero Saved Successfully",Toast.LENGTH_LONG).show()
        }


    }
}

