package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fauth: FirebaseAuth = FirebaseAuth.getInstance()
        val mLogoutBtn: Button = findViewById(R.id.mainlogout)
        timetable.setOnClickListener()
        {
            startActivity(Intent(this, TimeTable::class.java))
        }
        

    }





}

