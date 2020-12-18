package com.example.studentapp.Ques

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

var adapter: adapterQuestions? = null
class Questions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val recviewQues = findViewById<RecyclerView>(R.id.ques_recview)

        val options: FirebaseRecyclerOptions<Model_questions> = FirebaseRecyclerOptions.Builder<Model_questions>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("Questions"),
                Model_questions::class.java
            )
            .build()

        adapter = adapterQuestions(options)
        recviewQues.adapter = adapter

        val fb: FloatingActionButton = findViewById(R.id.qadd)
        fb.setOnClickListener{
            val intent = Intent(this, AddQuestions::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }
}
