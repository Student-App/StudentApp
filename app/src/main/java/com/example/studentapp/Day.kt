package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


var adapter: myadapter? = null
class Day : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val recview = findViewById<RecyclerView>(R.id.recview)

        val text = intent.getStringExtra("ID")
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""

        val options: FirebaseRecyclerOptions<Model> = FirebaseRecyclerOptions.Builder<Model>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("/TimeTable/$userId/Day/$text/Course"),
                Model::class.java
            )
            .build()

        adapter = myadapter(options,text)
        recview.adapter = adapter

        val fb:FloatingActionButton = findViewById(R.id.fadd)
        fb.setOnClickListener{
            val intent = Intent(this, AddData::class.java)
            intent.putExtra("day", text)
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