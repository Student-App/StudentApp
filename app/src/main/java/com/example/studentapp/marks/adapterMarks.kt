package com.example.studentapp.marks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth

class adapterMarks(options: FirebaseRecyclerOptions<Model_marks>, context: Context):
    FirebaseRecyclerAdapter<Model_marks, adapterMarks.myviewholder>(options) {
    protected override fun onBindViewHolder(
        holder: adapterMarks.myviewholder,
        position: Int,
        model: Model_marks
    ) {

        holder.course.text = model.course_name?.toUpperCase() ?: ""
        holder.TotalMarks.text = model.Total_marks
        holder.MarksObtained.text = model.Marks_obtained
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: ""

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
        var TotalMarks: TextView = itemView.findViewById<View>(R.id.total_marks) as TextView
        var MarksObtained: TextView = itemView.findViewById<View>(R.id.marks_obtained) as TextView

    }
}
