package com.example.studentapp.marks

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

var adapter: adapterMarks? = null
class Marks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marks)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val recviewMarks = findViewById<RecyclerView>(R.id.recview_marks)
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val options: FirebaseRecyclerOptions<Model_marks> = FirebaseRecyclerOptions.Builder<Model_marks>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("Marks/$userId/Courses"),
                Model_marks::class.java
            )
            .build()

        adapter = adapterMarks(options)
        recviewMarks.adapter = adapter

        val fb: FloatingActionButton = findViewById(R.id.madd)
        fb.setOnClickListener{
            val intent = Intent(this, AddMarks::class.java)
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