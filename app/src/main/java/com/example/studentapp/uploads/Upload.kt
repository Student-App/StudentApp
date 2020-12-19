package com.example.studentapp.uploads

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.Messenger.ChatLog
import com.example.studentapp.Messenger.LatestMessageRow
import com.example.studentapp.Messenger.NewMessage
import com.example.studentapp.Model
import com.example.studentapp.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_upload.*

class Upload : AppCompatActivity() {
//    var adapter: adapterUpload? = null


    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        file_recview.adapter = adapter
        file_recview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        adapter.setOnItemClickListener{item, view->
//            var intent = Intent.ACTION_VIEW
//            val row = item as adapterUpload
//            intent = Uri.parse(row.pdfFile.file_url.toString()).toString()
        }

        val text = intent.getStringExtra("ID")
        val cname = intent.getStringExtra("cname")
        val dept = intent.getStringExtra("dept")

        new_upload.setOnClickListener{
            val intent = Intent(this, AddFile::class.java)
            intent.putExtra("ID", text)
            intent.putExtra("dept",dept)
            startActivity(intent)
        }


        val ref = FirebaseDatabase.getInstance().getReference("Uploads/$dept/Courses/$text/files")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val pdfFile = snapshot.getValue(Model_upload::class.java) ?: return

                adapter.add(adapterUpload(pdfFile))
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }


        })









//        val options: FirebaseRecyclerOptions<Model_upload> = FirebaseRecyclerOptions.Builder<Model_upload>()
//            .setQuery(
//                FirebaseDatabase.getInstance().reference.child("Resources/Department/$text/Courses/$text/Files"),
//                Model_upload::class.java
//            )
//            .build()
////
//        adapter = adapterUpload(options)


    }

//    val adapter = GroupAdapter<ViewHolder>()


//    override fun onStart() {
//        super.onStart()
//        adapter!!.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter!!.stopListening()
//    }
}