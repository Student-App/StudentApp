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

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fauth: FirebaseAuth = FirebaseAuth.getInstance()
        val mEmail: EditText = findViewById(R.id.email)
        val mPassword: EditText = findViewById(R.id.password)
        val mRegister: TextView = findViewById(R.id.createNew)
        val mLoginBtn: Button = findViewById(R.id.LoginBtn)

        mLoginBtn.setOnClickListener{
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
            fauth.signInWithEmailAndPassword(input_email,input_pass).addOnCompleteListener { task: Task<AuthResult> ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT ).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }else{
                    Toast.makeText(this, "Error!" + (task.exception?.message ?: "!"), Toast.LENGTH_SHORT ).show()
                }
            }

        }

        mRegister.setOnClickListener{
            startActivity(Intent(this, Register::class.java))
        }

    }
}