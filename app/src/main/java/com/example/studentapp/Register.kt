package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fauth: FirebaseAuth = FirebaseAuth.getInstance()
        //val mFullName:EditText  = findViewById(R.id.fullName)
        val mEmail:EditText  = findViewById(R.id.email)
        val mPassword:EditText  = findViewById(R.id.password)
        val mLogin:TextView = findViewById(R.id.alreadyRegistered)
        val mRegisterBtn:Button = findViewById(R.id.RegisterBtn)

        if(fauth.currentUser!= null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        mRegisterBtn.setOnClickListener{
            val input_email = mEmail.text.toString().trim()
            val input_pass = mPassword.text.toString().trim()
            if(input_email.isEmpty()){
                mEmail.error = "Email is required"
                return@setOnClickListener
            }
            if(input_pass.isEmpty()){
                mPassword.error = "Password is required"
                return@setOnClickListener
            }
            fauth.createUserWithEmailAndPassword(input_email,input_pass).addOnCompleteListener { task: Task<AuthResult> ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT ).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }else{
                    Toast.makeText(this, "Error!" + (task.exception?.message ?: "!"), Toast.LENGTH_SHORT ).show()
                }
            }

        }

        mLogin.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
        }
    }
}