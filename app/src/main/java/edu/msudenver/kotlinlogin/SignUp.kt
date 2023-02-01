package edu.msudenver.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class SignUP : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        
        // Variables
        val username = findViewById<View>(R.id.username) as EditText
        val password = findViewById<View>(R.id.password) as EditText
        val password2 = findViewById<View>(R.id.repassword) as EditText
        val regbtn = findViewById<View>(R.id.signupbtn) as MaterialButton
        
        
        regbtn.setOnClickListener { v ->
            
            // Screen holding variables and intent
            val username1 = username.text.toString()
            val passwordString1 = password.text.toString()
            val passwordString2 = password2.text.toString()
            val mainScreenIntent = Intent()
            mainScreenIntent.setClass(v.context, MainActivity::class.java)
            
            // Go back to login screen after accepting user credentials.
            if (passwordString1 == passwordString2)
            {
                Toast.makeText(this@SignUP, "Welcome $username1",
                    Toast.LENGTH_SHORT).show()
                startActivity(mainScreenIntent)
            } // End if
            
            // If passwords don't match, let the user know.
            else
            {
                Toast.makeText(this@SignUP,
                    "Passwords don't match.\nPlease try again.",
                    Toast.LENGTH_SHORT).show()
            } // End else
        } // End onClick) // End regbtn.setOnClickListener
    } // End onCreate
} // End SignUp
