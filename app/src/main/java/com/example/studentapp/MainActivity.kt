package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}