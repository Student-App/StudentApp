package com.example.studentapp.Ques

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

class AddQuestions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_questions)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val submit: Button = findViewById(R.id.submit_question)
        submit.setOnClickListener {
            processInsert()
        }
    }
    private fun processInsert() {
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val quesTitle: TextInputLayout = findViewById(R.id.add_ques_title)
        val quesDetails: TextInputLayout = findViewById(R.id.add_question_details)
        val map = mutableMapOf<String, Any?>()
        map["ques_title"] = quesTitle.editText?.text.toString()
        map["ques_detail"] = quesDetails.editText?.text.toString()
        map["author_id"] = userId
        map["answer_count"] = "0"

        FirebaseDatabase.getInstance().reference.child("Questions").push()
            .setValue(map)
            .addOnSuccessListener {
                quesDetails.editText?.setText("")
                quesTitle.editText?.setText("")
                Toast.makeText(applicationContext,"Question added Successfully", Toast.LENGTH_LONG ).show()
                val intent = Intent(this, Questions::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error! Try again.", Toast.LENGTH_LONG ).show()
            }
    }
}