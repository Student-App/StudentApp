package com.example.studentapp.attendance

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

var adapter: adapterAttendance? = null
class Attendance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val recviewAttend = findViewById<RecyclerView>(R.id.recview_attend)
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val options: FirebaseRecyclerOptions<Model_attendance> = FirebaseRecyclerOptions.Builder<Model_attendance>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("Attendance/$userId/Courses"),
                Model_attendance::class.java
            )
            .build()

        adapter = adapterAttendance(options)
        recviewAttend.adapter = adapter

        val fb: FloatingActionButton = findViewById(R.id.add_attendance)
        fb.setOnClickListener{
            val intent = Intent(this, AddAttendance::class.java)
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