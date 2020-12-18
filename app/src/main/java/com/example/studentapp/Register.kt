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
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val fauth: FirebaseAuth = FirebaseAuth.getInstance()
        val mFullName: TextInputLayout = findViewById(R.id.fullName)
        val mEmail:TextInputLayout = findViewById(R.id.email)
        val mPassword:TextInputLayout  = findViewById(R.id.password)
        val mLogin:TextView = findViewById(R.id.alreadyRegistered)
        val mRegisterBtn:Button = findViewById(R.id.RegisterBtn)

        mRegisterBtn.setOnClickListener{
            progressBar2.visibility = View.VISIBLE
            val inputEmail = mEmail.editText?.text.toString().trim()
            val inputPass = mPassword.editText?.text.toString().trim()
            if(inputEmail.isEmpty()){
                mEmail.error = "Email is required"
                return@setOnClickListener
            }
            if(inputPass.isEmpty()){
                mPassword.error = "Password is required"
                return@setOnClickListener
            }
            fauth.createUserWithEmailAndPassword(inputEmail,inputPass).addOnCompleteListener { task: Task<AuthResult> ->
                if(task.isSuccessful){
                    progressBar2.visibility = View.INVISIBLE
                    val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?:""
                    val map = mutableMapOf<String, Any?>()

                    map["Name"] = mFullName.editText?.text.toString().trim()
                    map["Email"] = inputEmail
                    map["Image"] = ""

                    val user = Model.User(
                        userId,
                        map["Name"].toString(),
                        map["Image"].toString(),
                        map["Email"].toString()
                    )

                    FirebaseDatabase.getInstance().reference.child("Users/$userId")
                        .setValue(user)
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

