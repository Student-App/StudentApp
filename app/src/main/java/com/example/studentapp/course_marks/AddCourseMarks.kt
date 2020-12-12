package com.example.studentapp.course_marks

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

class AddCourseMarks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course_marks)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val submit: Button = findViewById(R.id.add_test_marks)
        submit.setOnClickListener {
            processInsert()
        }
    }

    private fun processInsert() {
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val tname: TextInputLayout = findViewById(R.id.add_test)
        val totalMarks: TextInputLayout = findViewById(R.id.test_marks)
        val marksObtained: TextInputLayout = findViewById(R.id.test_marks_obtained)
        val map = mutableMapOf<String, Any?>()
        map["test_name"] = tname.editText?.text.toString().toLowerCase()
        map["test_marks"] = totalMarks.editText?.text.toString().toLowerCase()
        map["test_marks_obtained"] = marksObtained.editText?.text.toString().toLowerCase()
        val text = intent.getStringExtra("ID")

        FirebaseDatabase.getInstance().reference.child("Marks/$userId/Courses/$text/Tests").push()
            .setValue(map)
            .addOnSuccessListener {
                tname.editText?.setText("")
                totalMarks.editText?.setText("")
                marksObtained.editText?.setText("")

                Toast.makeText(applicationContext,"Test added Successfully", Toast.LENGTH_LONG ).show()
                val intent = Intent(this, CourseMarks::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error! Try again.", Toast.LENGTH_LONG ).show()
            }
    }
}