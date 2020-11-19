package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val fauth: FirebaseAuth = FirebaseAuth.getInstance()
        val mEmail: TextInputLayout = findViewById(R.id.email)
        val mPassword: TextInputLayout = findViewById(R.id.password)
        val mRegister: TextView = findViewById(R.id.createNew)
        val mLoginBtn: Button = findViewById(R.id.LoginBtn)

        mLoginBtn.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            val inputEmail = mEmail.editText?.text.toString().trim()
            val inputPass = mPassword.editText?.text.toString().trim()
            if(inputEmail.isEmpty()){
                mEmail.error = "Email is required"
                return@setOnClickListener
            }
            if(inputPass.isEmpty()){
                progressBar.visibility = View.INVISIBLE
                mPassword.error = "Password is required"
                return@setOnClickListener
            }
            fauth.signInWithEmailAndPassword(inputEmail,inputPass).addOnCompleteListener { task: Task<AuthResult> ->
                if(task.isSuccessful){
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT ).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }else{
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Error!" + (task.exception?.message ?: "!"), Toast.LENGTH_SHORT ).show()
                }
            }

        }

        mRegister.setOnClickListener{
            startActivity(Intent(this, Register::class.java))
        }

    }
}