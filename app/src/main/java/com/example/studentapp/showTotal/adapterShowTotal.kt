package com.example.studentapp.showTotal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class adapterShowTotal(options: FirebaseRecyclerOptions<Model_showTotal>):
    FirebaseRecyclerAdapter<Model_showTotal, adapterShowTotal.myviewholder>(options) {
    override fun onBindViewHolder(
        holder: adapterShowTotal.myviewholder,
        position: Int,
        model: Model_showTotal
    ) {

        holder.testName.text = model.test_name
        holder.totalMarks.text = model.test_marks
        holder.marksObtained.text = model.test_marks_obtained
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.single_table_row, parent, false)
        return myviewholder(view)
    }

    inner class myviewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var testName: TextView = itemView.findViewById<View>(R.id.showTestName) as TextView
        var totalMarks: TextView = itemView.findViewById<View>(R.id.showTestTotalMarks) as TextView
        var marksObtained: TextView = itemView.findViewById<View>(R.id.showTestMarksObtained) as TextView

    }
}