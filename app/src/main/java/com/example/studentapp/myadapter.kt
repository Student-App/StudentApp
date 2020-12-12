package com.example.studentapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


var msg:String = ""
class myadapter(options: FirebaseRecyclerOptions<Model>, private val day:String) :
    FirebaseRecyclerAdapter<Model, myadapter.myviewholder>(options) {
    override fun onBindViewHolder(
        holder: myviewholder,
        position: Int,
        model: Model
    ) {
        holder.course.text = model.course_name?.toUpperCase() ?: ""
        holder.time.text = model.time
        holder.edit.setOnClickListener {
            val builder = AlertDialog.Builder(holder.course.context)
            val inflater = LayoutInflater.from(holder.course.context)
            val view = inflater.inflate(R.layout.dialogcontent,null)
            val courseName = view.findViewById<TextInputLayout>(R.id.edit_course)
            val time = view.findViewById<TimePicker>(R.id.edit_time)

            builder.setView(view)
            courseName.editText?.setText(model.course_name)
            val alert = builder.create()
            alert.show()

            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(alert.window?.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            alert.window?.attributes = layoutParams

            val submit:Button = view.findViewById<Button>(R.id.edit_submit)
            val cancel:Button = view.findViewById(R.id.edit_cancel)
            processTime(time)
            submit.setOnClickListener {
                val map = mutableMapOf<String, Any?>()
                map["course_name"] = courseName.editText?.text.toString().toLowerCase()
                map["time"] = msg
                val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
                getRef(position).key?.let { it1 ->
                    FirebaseDatabase.getInstance().reference.child("TimeTable/$userId/Day/$day/Course")
                        .child(it1).updateChildren(map)
                        .addOnSuccessListener {
                            alert.dismiss()
                        }
                        .addOnFailureListener{

                        }
                }
            }
            cancel.setOnClickListener{
                alert.dismiss()
            }
        }
        holder.delete.setOnClickListener {
            val builder = AlertDialog.Builder(holder.course.context)
            builder.setTitle("Delete Course")
            val cn = model.course_name
            builder.setMessage("Are you sure you want to delete $cn ??")
            builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
                val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    getRef(position).key?.let { it1 ->
                        FirebaseDatabase.getInstance().reference.child("TimeTable/$userId/Day/$day/Course")
                            .child(it1).removeValue()
                    }
                }

            })

            builder.show()
        }
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
        var edit:ImageButton = itemView.findViewById(R.id.edit)
        var delete: ImageButton = itemView.findViewById(R.id.delete)

    }

    private fun processTime(timePicker: TimePicker) {

        timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            val hr = if (hour < 10) "0" + hour else hour
            val min = if (minute < 10) "0" + minute else minute
            msg = "$hr : $min $am_pm"
        }
    }

}



