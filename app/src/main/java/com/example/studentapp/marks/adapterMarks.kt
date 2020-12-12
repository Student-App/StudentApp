package com.example.studentapp.marks

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.example.studentapp.course_marks.CourseMarks
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth

class adapterMarks(options: FirebaseRecyclerOptions<Model_marks>):
    FirebaseRecyclerAdapter<Model_marks, adapterMarks.myviewholder>(options) {
    override fun onBindViewHolder(
        holder: adapterMarks.myviewholder,
        position: Int,
        model: Model_marks
    ) {

        holder.course.text = model.course_name?.toUpperCase() ?: ""
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        holder.course.setOnClickListener {
            val text: String? = getRef(position).key
            val intent = Intent(holder.course.context, CourseMarks::class.java)
            intent.putExtra("ID", text)
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
