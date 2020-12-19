package com.example.studentapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.studentapp.Img2Text
import com.example.studentapp.Messenger.Messages
import com.example.studentapp.Ques.Questions
import com.example.studentapp.R
import kotlinx.android.synthetic.main.fragment_others.*


class OthersFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_others, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        h2t.setOnClickListener {
            val intent = Intent (getActivity(), Img2Text::class.java)
            getActivity()?.startActivity(intent)
        }
        ques.setOnClickListener {
            val intent = Intent (getActivity(), Questions::class.java)
            getActivity()?.startActivity(intent)
        }
        messages.setOnClickListener {
            val intent = Intent (getActivity(), Messages::class.java)
            getActivity()?.startActivity(intent)
        }

        inv.visibility = View.INVISIBLE


    }
}