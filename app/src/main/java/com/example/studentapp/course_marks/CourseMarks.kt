package com.example.studentapp.course_marks

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.example.studentapp.showTotal.TotalMarks
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_course_marks.*

var adapter: adapterCourseMarks? = null
class CourseMarks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_marks)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val recviewCourseMarks = findViewById<RecyclerView>(R.id.recview_course_marks)
        val cname = findViewById<TextView>(R.id.course_name)
        val text = intent.getStringExtra("ID")
        val courseName = intent.getStringExtra("cname")

        cname.text = courseName

        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        val options: FirebaseRecyclerOptions<Model_course_marks> = FirebaseRecyclerOptions.Builder<Model_course_marks>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("Marks/$userId/Courses/$text/Tests"),
                Model_course_marks::class.java
            )
            .build()

        adapter = adapterCourseMarks(options,text)
        val layoutManager: GridLayoutManager = GridLayoutManager(this, 2)
        recview_course_marks.layoutManager = layoutManager
        recviewCourseMarks.adapter = adapter

        val fb: FloatingActionButton = findViewById(R.id.cm_add)
        fb.setOnClickListener{
            val intent = Intent(this, AddCourseMarks::class.java)
            intent.putExtra("ID", text)
            startActivity(intent)
        }

        val showTotal: Button = findViewById(R.id.show_total)
        showTotal.setOnClickListener {
            val intent = Intent(this, TotalMarks::class.java)
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