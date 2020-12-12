package com.example.studentapp.marks

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

class AddMarks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_marks)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val submit: Button = findViewById(R.id.add_subject)
        submit.setOnClickListener {
            processInsert()
        }
    }
    private fun processInsert() {
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val cname: TextInputLayout = findViewById(R.id.add_course_name)
        val map = mutableMapOf<String, Any?>()
        map["course_name"] = cname.editText?.text.toString().toLowerCase()

        FirebaseDatabase.getInstance().reference.child("Marks/$userId/Courses").push()
            .setValue(map)
            .addOnSuccessListener {
                cname.editText?.setText("")
                Toast.makeText(applicationContext,"Course added Successfully", Toast.LENGTH_LONG ).show()
                val intent = Intent(this, Marks::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error! Try again.", Toast.LENGTH_LONG ).show()
            }
    }
}