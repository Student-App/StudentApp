package com.example.studentapp.ans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class adapterAnswer(options: FirebaseRecyclerOptions<Model_answers>):
    FirebaseRecyclerAdapter<Model_answers, adapterAnswer.myviewholder>(options) {
    override fun onBindViewHolder(
        holder: adapterAnswer.myviewholder,
        position: Int,
        model: Model_answers
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


        holder.answer.text = model.answer

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.singleanswer, parent, false)
        return myviewholder(view)
    }

    inner class myviewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var authorName: TextView = itemView.findViewById<View>(R.id.author_name) as TextView
        var answer: TextView = itemView.findViewById<View>(R.id.answer) as TextView

    }
}
