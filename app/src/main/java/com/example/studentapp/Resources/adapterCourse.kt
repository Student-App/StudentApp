package com.example.studentapp.Resources

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.example.studentapp.uploads.Upload
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class adapterCourse(options: FirebaseRecyclerOptions<Model_course>,private val dept:String):
    FirebaseRecyclerAdapter<Model_course, adapterCourse.myviewholder>(options) {
    override fun onBindViewHolder(
        holder: adapterCourse.myviewholder,
        position: Int,
        model: Model_course
    ) {

        holder.course.text = model.course_name?.toUpperCase() ?: ""
        holder.course.setOnClickListener {
            val text: String? = getRef(position).key
            val intent = Intent(holder.course.context, Upload::class.java)
            intent.putExtra("ID", text)
            intent.putExtra("cname", model.course_name)
            intent.putExtra("dept",dept)
            holder.course.context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.singlesubject, parent, false)
        return myviewholder(view)
    }

    inner class myviewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var course: TextView = itemView.findViewById<View>(R.id.course) as TextView

    }
}