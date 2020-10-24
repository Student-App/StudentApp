package com.example.studentapp.attendance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

var adapter: adapterAttendance? = null
class Attendance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)
        val recviewAttend = findViewById<RecyclerView>(R.id.recview_attend)

        val options: FirebaseRecyclerOptions<Model_attendance> = FirebaseRecyclerOptions.Builder<Model_attendance>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("/Courses/s1"),
                Model_attendance::class.java
            )
            .build()

        adapter = adapterAttendance(options)
        recviewAttend.adapter = adapter

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