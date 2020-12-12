package com.example.studentapp.Resources

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.studentapp.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.singlerow.view.*

class ViewResources : AppCompatActivity() {


    val notes_view : ListView = findViewById(R.id.resourceListView)
    val database_ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Notes")
    var uploads = mutableListOf<ClassNotes?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_resources)

        viewAllFiles()
        notes_view.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val up: ClassNotes? = uploads[position]
            val intent = Intent()
            intent.data = Uri.parse(up!!.url)
            startActivity(intent)
//            intent.action = Intent.ACTION_GET_CONTENT


        }

    }

    private fun viewAllFiles() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for ( postSnapshot in dataSnapshot.getChildren())
                {
                    val notes: ClassNotes? = postSnapshot.getValue(ClassNotes::class.java)
                    uploads.add(notes)

                }

                val names = Array<String?>(uploads.size) {null}
                for( i in 1..uploads.size)
                {
                    names[i] = uploads.get(i)!!.name
                }

                val adapter : ArrayAdapter<String?> = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, names)
                notes_view.adapter = adapter

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        }

        database_ref.addValueEventListener(postListener)


    }
}





