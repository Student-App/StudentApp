package com.example.studentapp.course_marks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class adapterCourseMarks(options: FirebaseRecyclerOptions<Model_course_marks>):
    FirebaseRecyclerAdapter<Model_course_marks, adapterCourseMarks.myviewholder>(options) {
    override fun onBindViewHolder(
        holder: adapterCourseMarks.myviewholder,
        position: Int,
        model: Model_course_marks
    ) {

        holder.testName.text = model.test_name?.toUpperCase() ?: ""
        holder.totalMarks.text = model.test_marks
        holder.marksObtained.text = model.test_marks_obtained

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.singletest, parent, false)
        return myviewholder(view)
    }

    inner class myviewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var testName: TextView = itemView.findViewById<View>(R.id.test_name) as TextView
        var totalMarks: TextView = itemView.findViewById<View>(R.id.total_marks_value) as TextView
        var marksObtained: TextView = itemView.findViewById<View>(R.id.marks_obtained_value) as TextView

    }
}
