package com.example.studentapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class myadapter(options: FirebaseRecyclerOptions<Model>) :
    FirebaseRecyclerAdapter<Model, myadapter.myviewholder>(options) {
    protected override fun onBindViewHolder(
        holder: myviewholder,
        position: Int,
        model: Model
    ) {

        holder.course.text = model.course_name
        holder.time.text = model.time
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.singlecourse, parent, false)
        return myviewholder(view)
    }

    inner class myviewholder(itemView: View) :
        ViewHolder(itemView) {
        var course: TextView = itemView.findViewById<View>(R.id.coursetext) as TextView
        var time: TextView = itemView.findViewById<View>(R.id.time) as TextView

    }

}

