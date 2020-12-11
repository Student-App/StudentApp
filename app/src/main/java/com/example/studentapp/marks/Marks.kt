package com.example.studentapp.marks

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerOptions
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

        adapter = adapterMarks(options,applicationContext)
        recviewMarks.adapter = adapter

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