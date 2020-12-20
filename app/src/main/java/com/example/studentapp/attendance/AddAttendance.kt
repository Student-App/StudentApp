package com.example.studentapp.attendance

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddAttendance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_attendance)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val submit: Button = findViewById(R.id.submit_attendance_course)
        submit.setOnClickListener {
            processInsert()
        }
    }

    private fun processInsert() {
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val cName: TextInputLayout = findViewById(R.id.add_attendance_course)

        val map = mutableMapOf<String, Any?>()
        map["test_name"] = cName.editText?.text.toString().toLowerCase()



        FirebaseDatabase.getInstance().reference.child("Attendance/$userId/Courses").push()
            .setValue(map)
            .addOnSuccessListener {
                cName.editText?.setText("")

                Toast.makeText(applicationContext,"Course added Successfully", Toast.LENGTH_LONG ).show()
                val intent = Intent(this, Attendance::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error! Try again.", Toast.LENGTH_LONG ).show()
            }
    }
}