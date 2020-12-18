package com.example.studentapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*
import kotlinx.android.synthetic.main.user_chat.view.*


class  LatestMessageRow(val chatMessage: Model.ChatMessage): Item<ViewHolder>()
    {
        var chatPartnerUser: Model.User? = null

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.message_latest_messages.text = chatMessage.text
            val chatPartnerID : String
            if(chatMessage.fromID == FirebaseAuth.getInstance().uid)
            {
                chatPartnerID = chatMessage.toID
            }
            else{
                chatPartnerID = chatMessage.fromID
            }
            val ref = FirebaseDatabase.getInstance().getReference("/Users/$chatPartnerID")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatPartnerUser = snapshot.getValue(Model.User::class.java)
                    viewHolder.itemView.username_latest_messages.text = chatPartnerUser?.name

//                    val targetImageView = viewHolder.itemView.imageView_newMessage
//                    if (user?.Image != null) {
//                        Picasso.get().load(user?.Image).into(targetImageView)
//                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }

            })
        }

        override fun getLayout(): Int {
            return R.layout.latest_message_row
        }

    }
