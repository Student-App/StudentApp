package com.example.studentapp.Resources

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase

class AddCourse : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val submit: Button = findViewById(R.id.submit_course)
        submit.setOnClickListener {
            processInsert()
        }
    }
    private fun processInsert() {
        val cname: TextInputLayout = findViewById(R.id.add_dept_course)
        val text = intent.getStringExtra("ID")
        val map = mutableMapOf<String, Any?>()
        map["course_name"] = cname.editText?.text.toString().toLowerCase()

        FirebaseDatabase.getInstance().reference.child("Uploads/$text/Courses").push()
            .setValue(map)
            .addOnSuccessListener {
                cname.editText?.setText("")
                Toast.makeText(applicationContext,"Course added Successfully", Toast.LENGTH_LONG ).show()
                val intent = Intent(this, Course::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error! Try again.", Toast.LENGTH_LONG ).show()
            }
    }
}