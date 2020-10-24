package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TimeTable : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)

        val day1 : androidx.cardview.widget.CardView = findViewById(R.id.monday)
        val day2 : androidx.cardview.widget.CardView = findViewById(R.id.tuesday)
        val day3 : androidx.cardview.widget.CardView = findViewById(R.id.wednesday)
        val day4 : androidx.cardview.widget.CardView = findViewById(R.id.thursday)
        val day5 : androidx.cardview.widget.CardView = findViewById(R.id.friday)
        val day6 : androidx.cardview.widget.CardView = findViewById(R.id.saturday)
        val day7 : androidx.cardview.widget.CardView = findViewById(R.id.sunday)

        day1.setOnClickListener{
            showTimeTable("Monday")
        }
        day2.setOnClickListener{
            showTimeTable("Tuesday")
        }
        day3.setOnClickListener{
            showTimeTable("Wednesday")
        }
        day4.setOnClickListener{
            showTimeTable("Thursday")
        }
        day5.setOnClickListener{
            showTimeTable("Friday")
        }
        day6.setOnClickListener{
            showTimeTable("Saturday")
        }
        day7.setOnClickListener{
            showTimeTable("Sunday")
        }


    }

     private fun showTimeTable(text: String) {
         val intent = Intent(this, Day::class.java)
         intent.putExtra("ID", text)
         startActivity(intent)
    }
}