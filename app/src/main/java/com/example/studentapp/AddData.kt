package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AddData : AppCompatActivity() {
    var msg:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        val submit:Button = findViewById(R.id.add_submit)
        processTime()
        submit.setOnClickListener {
            processInsert()
        }
    }

    private fun processInsert() {
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val cname:EditText = findViewById(R.id.add_course)
        val map = mutableMapOf<String, Any?>()
        map["course_name"] = cname.text.toString().toLowerCase()
        map["time"] = msg

        val text = intent.getStringExtra("day")
        attendanceRecord()
        FirebaseDatabase.getInstance().reference.child("TimeTable/$userId/Day/$text/Course").push()
            .setValue(map)
            .addOnSuccessListener {
                cname.setText("")
                Toast.makeText(applicationContext,"Course added Successfully",Toast.LENGTH_LONG ).show()
                val intent = Intent(this, Day::class.java)
                intent.putExtra("ID", text)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error! Try again.",Toast.LENGTH_LONG ).show()
            }
    }

    private fun attendanceRecord() {
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val cname:EditText = findViewById(R.id.add_course)
        val map = mutableMapOf<String, Any?>()
        map["course_name"] = cname.text.toString().toLowerCase()
        map["Absence"] = "0"
//        val ref = FirebaseDatabase.getInstance().reference.child("Attendance/$userId/Courses")
//        ref.orderByChild("course_name").equalTo(cname.text.toString().toLowerCase()).addListenerForSingleValueEvent()
        FirebaseDatabase.getInstance().reference.child("Attendance/$userId/Courses").push()
            .setValue(map)
    }

    private fun processTime() {
        val timePicker = findViewById<TimePicker>(R.id.add_time)
        timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            val hr = if (hour < 10) "0" + hour else hour
            val min = if (minute < 10) "0" + minute else minute
            msg = "$hr : $min $am_pm"
        }
    }

}