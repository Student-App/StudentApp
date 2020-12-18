package com.example.studentapp.uploads

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

//var adapter: adapterUpload? = null
class Upload : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

//        val recviewUpload = findViewById<RecyclerView>(R.id.file_recview)
        val text = intent.getStringExtra("ID")
        val cname = intent.getStringExtra("cname")
        val dept = intent.getStringExtra("dept")
//        val options: FirebaseRecyclerOptions<Model_upload> = FirebaseRecyclerOptions.Builder<Model_upload>()
//            .setQuery(
//                FirebaseDatabase.getInstance().reference.child("Resources/Department/$text/Courses/$text/Files"),
//                Model_upload::class.java
//            )
//            .build()
//
//        adapter = adapterUpload(options)
//        recviewUpload.adapter = adapter

        val fb: FloatingActionButton = findViewById(R.id.new_upload)
        fb.setOnClickListener{
            val intent = Intent(this, AddFile::class.java)
            intent.putExtra("ID", text)
            intent.putExtra("dept",dept)
            startActivity(intent)
        }
    }

//    override fun onStart() {
//        super.onStart()
//        adapter!!.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter!!.stopListening()
//    }
}