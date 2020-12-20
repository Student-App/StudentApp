package com.example.studentapp.showTotal

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_total_marks.*


var adapter: adapterShowTotal? = null
class TotalMarks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_marks)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val totalMarksRecyclerView = findViewById<RecyclerView>(R.id.TotalMarksRecyclerView)
        val text = intent.getStringExtra("ID")
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""

        var courseTotalMarks = 0
        var courseMarksObtained = 0
        val query: Query = FirebaseDatabase.getInstance().reference.child("Marks/$userId/Courses/$text/Tests")

        query.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                //
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (ss in snapshot.children) {
                        val test = ss.getValue(Model_showTotal::class.java)!!
                        val test_total = test.test_marks
                        Log.e("Total", "$test_total")
                        courseTotalMarks += test.test_marks?.toInt() ?: 0
                        courseMarksObtained += test.test_marks_obtained?.toInt() ?: 0
                    }
                    CourseTotalMarks.text = courseTotalMarks.toString()
                    CourseMarksObtained.text = courseMarksObtained.toString()
                }
            }
        })




        val options: FirebaseRecyclerOptions<Model_showTotal> = FirebaseRecyclerOptions.Builder<Model_showTotal>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("Marks/$userId/Courses/$text/Tests"),
                Model_showTotal::class.java
            )
            .build()

        adapter = adapterShowTotal(options)
        totalMarksRecyclerView.adapter = adapter

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