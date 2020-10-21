package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TimeTable : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        val day : androidx.cardview.widget.CardView = findViewById(R.id.monday)
        day.setOnClickListener{
            startActivity(Intent(this, Day::class.java))
        }
    }
}