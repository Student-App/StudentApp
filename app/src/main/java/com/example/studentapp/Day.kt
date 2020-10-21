package com.example.studentapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

var adapter: myadapter? = null
class Day : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        val recview = findViewById<RecyclerView>(R.id.recview)


        val options: FirebaseRecyclerOptions<Model> = FirebaseRecyclerOptions.Builder<Model>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("students"),
                Model::class.java
            )
            .build()

        adapter = myadapter(options)
        recview.adapter = adapter

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