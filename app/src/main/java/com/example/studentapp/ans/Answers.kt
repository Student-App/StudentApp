package com.example.studentapp.ans

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

var adapter: adapterAnswer? = null
class Answers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val recviewAnswer = findViewById<RecyclerView>(R.id.recview_answers)

        val quesId = intent.getStringExtra("ID")
        val quesTitle = findViewById<TextView>(R.id.ques_title)
        val quesDetails = findViewById<TextView>(R.id.ques_detail)

        val query: Query = FirebaseDatabase.getInstance().reference.child("Questions/$quesId")
        query.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                //
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    quesTitle.text  = snapshot.child("ques_title").value.toString()
                    quesDetails.text = snapshot.child("ques_detail").value.toString()
                }
            }

        })


        val options: FirebaseRecyclerOptions<Model_answers> = FirebaseRecyclerOptions.Builder<Model_answers>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("Questions/$quesId/Answers"),
                Model_answers::class.java
            )
            .build()

        adapter = adapterAnswer(options)
        recviewAnswer.adapter = adapter

        val fb: FloatingActionButton = findViewById(R.id.ansAdd)
        fb.setOnClickListener{
            val intent = Intent(this, AddAnswers::class.java)
            intent.putExtra("ID", quesId)
            startActivity(intent)
            adapter!!.notifyDataSetChanged()
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