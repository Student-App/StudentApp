package com.example.studentapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.studentapp.R
import com.example.studentapp.Resources.Department
import com.example.studentapp.TimeTable
import com.example.studentapp.attendance.Attendance
import com.example.studentapp.marks.Marks
import kotlinx.android.synthetic.main.fragment_academic.*


class AcademicFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_academic, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        timetable.setOnClickListener {
            val intent = Intent (getActivity(), TimeTable::class.java)
            getActivity()?.startActivity(intent)
        }
        attendance.setOnClickListener {
            val intent = Intent (getActivity(), Attendance::class.java)
            getActivity()?.startActivity(intent)
        }
        marks.setOnClickListener {
            val intent = Intent (getActivity(), Marks::class.java)
            getActivity()?.startActivity(intent)
        }
        res.setOnClickListener {
            val intent = Intent (getActivity(), Department::class.java)
            getActivity()?.startActivity(intent)
        }

    }

}

