package com.example.studentapp.Resources

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R

class Department : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val dept1 : androidx.cardview.widget.CardView = findViewById(R.id.cse)
        val dept2 : androidx.cardview.widget.CardView = findViewById(R.id.ee)
        val dept3 : androidx.cardview.widget.CardView = findViewById(R.id.me)
        val dept4 : androidx.cardview.widget.CardView = findViewById(R.id.civil)


        dept1.setOnClickListener{
            showCourse("CSE")
        }
        dept2.setOnClickListener{
            showCourse("EE")
        }
        dept3.setOnClickListener{
            showCourse("ME")
        }
        dept4.setOnClickListener{
            showCourse("Civil")
        }



    }

    private fun showCourse(text: String) {
        val intent = Intent(this, Course::class.java)
        intent.putExtra("ID", text)
        startActivity(intent)
    }
}