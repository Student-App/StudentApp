package com.example.studentapp.Resources

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

var adapter: adapterCourse? = null
class Course : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val recviewCourse = findViewById<RecyclerView>(R.id.recview_course)
        val text = intent.getStringExtra("ID")
        val options: FirebaseRecyclerOptions<Model_course> = FirebaseRecyclerOptions.Builder<Model_course>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("Uploads/$text/Courses"),
                Model_course::class.java
            )
            .build()

        adapter = adapterCourse(options,text!!)
        recviewCourse.adapter = adapter

        val fb: FloatingActionButton = findViewById(R.id.cadd)
        fb.setOnClickListener{
            val intent = Intent(this, AddCourse::class.java)
            intent.putExtra("ID", text)
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