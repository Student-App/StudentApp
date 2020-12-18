package com.example.studentapp.ans

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.Ques.Questions
import com.example.studentapp.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddAnswers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_answers)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val submit: Button = findViewById(R.id.submit_answer)
        submit.setOnClickListener {
            processInsert()
        }
    }

    private fun processInsert() {
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val answer: TextInputLayout = findViewById(R.id.add_answer)
        val text = intent.getStringExtra("ID")


        val map = mutableMapOf<String, Any?>()
        map["author_id"] = userId
        map["answer"] = answer.editText?.text.toString()



        FirebaseDatabase.getInstance().reference.child("Questions/$text/Answers").push()
            .setValue(map)
            .addOnSuccessListener {
                answer.editText?.setText("")

                Toast.makeText(applicationContext,"Answer added Successfully", Toast.LENGTH_LONG ).show()
                val intent = Intent(this, Questions::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error! Try again.", Toast.LENGTH_LONG ).show()
            }

    }
}