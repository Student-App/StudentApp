package com.example.studentapp.Ques

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.example.studentapp.ans.Answers
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class adapterQuestions(options: FirebaseRecyclerOptions<Model_questions>):
    FirebaseRecyclerAdapter<Model_questions, adapterQuestions.myviewholder>(options) {
    override fun onBindViewHolder(
        holder: adapterQuestions.myviewholder,
        position: Int,
        model: Model_questions
    ) {

        val userId = model.author_id
        val query: Query = FirebaseDatabase.getInstance().reference.child("Users/$userId")
        query.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                //
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    holder.authorName.text  = snapshot.child("name").value.toString()
                }else{
                    holder.authorName.text  = "abc"
                }
            }

        })

        holder.questionTitle.text = model.ques_title
        holder.questionTitle.setOnClickListener {
            val text: String? = getRef(position).key
            val intent = Intent(holder.questionTitle.context, Answers::class.java)
            intent.putExtra("ID", text)
            holder.questionTitle.context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.single_question, parent, false)
        return myviewholder(view)
    }

    inner class myviewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var questionTitle: TextView = itemView.findViewById<View>(R.id.question_title) as TextView
        var authorName: TextView = itemView.findViewById<View>(R.id.authorName) as TextView
    }
}