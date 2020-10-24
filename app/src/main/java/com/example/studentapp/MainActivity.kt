package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.attendance.Attendance
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timetable.setOnClickListener()
        {
            startActivity(Intent(this, TimeTable::class.java))
        }

        attendance.setOnClickListener(){
            startActivity(Intent(this, Attendance::class.java))
        }

    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }




}

