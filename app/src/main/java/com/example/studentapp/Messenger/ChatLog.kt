package com.example.studentapp.Messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studentapp.Model
import com.example.studentapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLog : AppCompatActivity() {
    val adapter = GroupAdapter<ViewHolder>()
    var toUser: Model.User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        toUser = intent.getParcelableExtra<Model.User>(NewMessage.USER_KEY)
        supportActionBar?.title = toUser?.name

        recyclerview_chatlog.adapter = adapter

        listenForMessages()

        send_chatlog.setOnClickListener{
            performSendMessage()
        }

    }

    private fun listenForMessages() {
        val fromID = FirebaseAuth.getInstance().uid
        val toID = toUser?.id
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromID/$toID")
        ref.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(Model.ChatMessage::class.java)
                if (chatMessage != null) {

                    if(chatMessage.fromID == FirebaseAuth.getInstance().uid){
                        val currentUser = Messages.currentUser ?: return
                    adapter.add(ChatFromItem(chatMessage.text, currentUser))
                    }
                    else
                    {
                        adapter.add(ChatToItem(chatMessage.text, toUser!!))
                    }
                }
                recyclerview_chatlog.scrollToPosition(adapter.itemCount-1)
            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

        })
    }


    private fun performSendMessage() {
        val fromID = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<Model.User>(NewMessage.USER_KEY)
        val toID = user.id
        val text = editText_chatlog.text.toString()
        if(fromID==null) return
//        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromID/$toID").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toID/$fromID").push()


        val chatMessage = Model.ChatMessage(
            reference.key!!,
            text,
            fromID,
            toID,
            System.currentTimeMillis() / 1000
        )
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                editText_chatlog.text.clear()
                recyclerview_chatlog.scrollToPosition(adapter.itemCount -1)
            }
        toReference.setValue(chatMessage)
        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromID/$toID")
        latestMessageRef.setValue(chatMessage)


        val tolatestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toID/$fromID")
        tolatestMessageRef.setValue(chatMessage)

    }


}


class ChatFromItem(val text: String, val user: Model.User): Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_fromchat.text = text
        val uri = user.image
        val targetImageView = viewHolder.itemView.imageView_chatfromrow
        if (uri != "") {
            Picasso.get().load(uri).into(targetImageView)
        }
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

}
class ChatToItem(val text: String, val user: Model.User): Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_tochat.text = text
        val uri = user.image
        val targetImageView = viewHolder.itemView.imageView_chattorow
        if (uri != "") {
            Picasso.get().load(uri).into(targetImageView)
        }
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

}