package com.example.studentapp.course_marks

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class adapterCourseMarks(options: FirebaseRecyclerOptions<Model_course_marks>, private val courseId:String):
    FirebaseRecyclerAdapter<Model_course_marks, adapterCourseMarks.myviewholder>(options) {
    override fun onBindViewHolder(
        holder: adapterCourseMarks.myviewholder,
        position: Int,
        model: Model_course_marks
    ) {

        holder.testName.text = model.test_name?.toUpperCase() ?: ""
        holder.totalMarks.text = model.test_marks
        holder.marksObtained.text = model.test_marks_obtained
        holder.test.setOnClickListener{
            val popupMenu = PopupMenu(holder.test.context,it)
            popupMenu.menuInflater.inflate(R.menu.pop_menu, popupMenu.menu)
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener {
                 when (it.itemId) {
                    R.id.marks_delete ->{
                        val builder = AlertDialog.Builder(holder.test.context)
                        builder.setTitle("Delete Course")
                        val tn = model.test_name
                        builder.setMessage("Are you sure you want to delete $tn ??")
                        builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
                            val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                getRef(position).key?.let { it1 ->
                                    FirebaseDatabase.getInstance().reference.child("Marks/$userId/Courses/$courseId/Tests")
                                        .child(it1).removeValue()

                                }
                            }
                        })
                        builder.show()
                        true
                    }
                     else -> {
                          true
                     }
                 }
            }
        }


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
        var test: CardView = itemView.findViewById(R.id.test_card)

    }
}
