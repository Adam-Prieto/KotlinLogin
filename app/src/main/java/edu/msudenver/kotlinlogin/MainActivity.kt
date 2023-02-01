package edu.msudenver.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.*
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity()
{
    
    // Google Sign-In variables
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    var googleBtn: ImageView? = null
    
    
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // User Sign-Up
        val signUpBtn = findViewById<View>(R.id.signUpBtn) as MaterialButton
        signUpBtn.setOnClickListener { v ->
            val intent1 = Intent()
            intent1.setClass(v.context, SignUP::class.java)
            startActivity(intent1)
        }
        
        val username = findViewById<View>(R.id.username) as TextView
        val password = findViewById<View>(R.id.password) as TextView
        val loginBtn = findViewById<View>(R.id.loginbtn) as MaterialButton
        passwordLogIn(username, password, loginBtn)
        
        
        // Google Sign-In
        
    } // End onCreate
    
    private fun passwordLogIn(username: TextView, password: TextView,
                              loginBtn: MaterialButton)
    {
        // Login if user enters correct information
        loginBtn.setOnClickListener {
            
            // Correct login
            if (username.text.toString() == "admin" && password.text.toString() == "admin")
            {
                val directoryIntent = Intent()
                directoryIntent.setClass(this, DirectoryActivity::class.java)
                startActivity(directoryIntent)
                
            } // End if
            
            // Wrong login
            else
            {
                Toast.makeText(this@MainActivity, "LOGIN FAILED",
                    Toast.LENGTH_SHORT).show()
            } // End else
        } // End loginBtn.setOnClickListener
    } // End passwordLogIn
    
} // End MainActivity Class