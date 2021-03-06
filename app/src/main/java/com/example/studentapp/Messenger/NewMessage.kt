package com.example.studentapp.Messenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.Model
import com.example.studentapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_chat.view.*

class NewMessage : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"
        fetchUsers()
    }
    companion object{
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers()
    {
        val ref =  FirebaseDatabase.getInstance().getReference("/Users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val adapter = GroupAdapter<ViewHolder>()


                snapshot.children.forEach {
                    Log.d("New Message", it.toString())
                    val user = it.getValue(Model.User::class.java)
//                    Log.d("User", user)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }
                adapter.setOnItemClickListener{item, view->
                    val userItem = item as UserItem
                    val intent = Intent(view.context, ChatLog::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)
                    finish()
                }
                recyclerview_newmessage.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}




class UserItem(val user: Model.User): Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int) {
        if (user != null) {
            viewHolder.itemView.username_textview_new_message.text = user.name
            val uri = user.image
            val targetImageView = viewHolder.itemView.imageView_newMessage
            if (uri != "") {
                Picasso.get().load(uri).into(targetImageView)
            }
        }

    }

    override fun getLayout(): Int {
        return R.layout.user_chat
    }


}