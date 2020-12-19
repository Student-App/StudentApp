package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.studentapp.adapters.ViewPagerAdapter
import com.example.studentapp.fragments.AcademicFragment
import com.example.studentapp.fragments.OthersFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setUpTabs()
        nav_view.bringToFront()
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,drawer_layout,toolbar,R.string.Open,R.string.Close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.nav_home)

        //Current User
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        // header View
        val headView = nav_view.getHeaderView(0)
        val name: TextView = headView.findViewById(R.id.student_name)
        val email: TextView = headView.findViewById(R.id.student_email)

        // Accessing Name and email of current user
        val query: Query = FirebaseDatabase.getInstance().reference.child("Users/$userId")
        query.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                //
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val sName:String = snapshot.child("name").value.toString()
                    name.text = sName

                    val sEmail:String = snapshot.child("email").value.toString()
                    email.text = sEmail
                }
            }

        })

        //val timetable: CardView = findViewById(R.id.timetable)!!
//
//        timetable.setOnClickListener()
//        {
//            startActivity(Intent(this, TimeTable::class.java))
//        }
//
//        val attendance: CardView = findViewById(R.id.attendance)
//        attendance.setOnClickListener {
//            startActivity(Intent(this, Attendance::class.java))
//        }
//
//        val marks: CardView = findViewById(R.id.marks)
//        marks.setOnClickListener {
//            startActivity(Intent(this, Marks::class.java))
//        }
//
//        val res: CardView = findViewById(R.id.res)
//        res.setOnClickListener {
//            startActivity(Intent(this, Department::class.java))
//        }
//
//        val h2t: CardView = findViewById(R.id.h2t)
//        h2t.setOnClickListener {
//            startActivity(Intent(this, Img2Text::class.java))
//        }
//
//        val ques: CardView = findViewById(R.id.ques)
//        ques.setOnClickListener {
//            startActivity(Intent(this, Questions::class.java))
//        }
//
//        val messages: CardView = findViewById(R.id.messages)
//        messages.setOnClickListener()
//        {
//            startActivity(Intent(this, Messages::class.java))
//
//        }

    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }

    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_home -> {
                drawer_layout.closeDrawer(GravityCompat.START)
                true
            }
            R.id.nav_profile ->{
                startActivity(Intent(this, Profile::class.java))
                true
            }
            else -> {
                logout()
                true
            }
        }

    }

    private fun setUpTabs() {
        val adapter=  ViewPagerAdapter( supportFragmentManager)
        adapter.addFragment(AcademicFragment(), "Academic")
        adapter.addFragment(OthersFragment(),"Others")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }


}

