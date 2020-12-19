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
            val intent = Intent (activity, TimeTable::class.java)
            activity?.startActivity(intent)
        }
        attendance.setOnClickListener {
            val intent = Intent (activity, Attendance::class.java)
            activity?.startActivity(intent)
        }
        marks.setOnClickListener {
            val intent = Intent (activity, Marks::class.java)
            activity?.startActivity(intent)
        }
        res.setOnClickListener {
            val intent = Intent (activity, Department::class.java)
            activity?.startActivity(intent)
        }

    }

}

