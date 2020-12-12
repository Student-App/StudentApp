package com.example.studentapp.attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class adapterAttendance(options: FirebaseRecyclerOptions<Model_attendance>) :
    FirebaseRecyclerAdapter<Model_attendance, adapterAttendance.myviewholder>(options) {
    override fun onBindViewHolder(
        holder: adapterAttendance.myviewholder,
        position: Int,
        model: Model_attendance
    ) {

        holder.course.text = model.course_name?.toUpperCase() ?: ""
        holder.Absence.text = model.Absence
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
        holder.plus.setOnClickListener {
            var absent: Int = model.Absence?.toInt() ?: 0
            absent++
            val map = mutableMapOf("Absence" to absent.toString())
            getRef(position).key?.let { it1 ->
                FirebaseDatabase.getInstance().reference.child("Attendance/$userId/Courses")
                    .child(it1).updateChildren(map as Map<String, Any>)
            }
        }

        holder.minus.setOnClickListener {
            var absent: Int = model.Absence?.toInt() ?: 0

            if(absent>0){
                absent--
            }
            val map = mutableMapOf("Absence" to absent.toString())
            getRef(position).key?.let { it1 ->
                FirebaseDatabase.getInstance().reference.child("Attendance/$userId/Courses")
                    .child(it1).updateChildren(map as Map<String, Any>)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.singlerow, parent, false)
        return myviewholder(view)
    }

    inner class myviewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var course: TextView = itemView.findViewById<View>(R.id.course) as TextView
        var Absence: TextView = itemView.findViewById<View>(R.id.count) as TextView
        var plus: ImageButton = itemView.findViewById(R.id.plus) as ImageButton
        var minus: ImageButton = itemView.findViewById(R.id.minus) as ImageButton

    }

}
