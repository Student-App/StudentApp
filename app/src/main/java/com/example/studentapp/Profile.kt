package com.example.studentapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.update_password.*
import kotlinx.android.synthetic.main.update_password.view.*
import java.io.IOException
import kotlin.random.Random

class Profile : AppCompatActivity(), PermissionListener {
    private val currentUser = FirebaseAuth.getInstance().currentUser
    var flag:Boolean = false
    var pFlag:Boolean = false
    private lateinit var filePath: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val userId = currentUser?.uid
        val name:TextInputLayout = findViewById(R.id.profile_name)
        var sName:String = ""
        val query: Query = FirebaseDatabase.getInstance().reference.child("Users/$userId")
            query.addValueEventListener(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    //
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        sName = snapshot.child("Name").value.toString()
                        name.editText?.setText(sName)
                    }
                }

            })


        //Profile Image
        update_image.setOnClickListener{
            Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(this)
                .check()
        }

        // Change Password
        profile_password.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.update_password, null)

            builder.setView(view)

            val alert = builder.create()
            alert.show()

            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(alert.window?.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            alert.window?.attributes = layoutParams

            val pass: TextInputLayout = view.findViewById(R.id.curr_pass)
            val newPass: TextInputLayout = view.findViewById(R.id.New_pass)
            val confPass: TextInputLayout = view.findViewById(R.id.confirm_new_pass)
            val progressBar:ProgressBar = view.findViewById(R.id.progressBar3)
            val submit: Button = view.findViewById(R.id.Update_pass)

            submit.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val curPass = pass.editText?.text.toString().trim()
                if(curPass.isEmpty()) {
                    progressBar.visibility = View.INVISIBLE
                    curr_pass.error = "Current Password is required"
                    curr_pass.requestFocus()
                    return@setOnClickListener
                }
                val updatedPass = newPass.editText?.text.toString().trim()
                val confirmPass = confPass.editText?.text.toString().trim()
                if(updatedPass.isEmpty() || updatedPass.length<6) {
                    progressBar.visibility = View.INVISIBLE
                    view.New_pass.error = "Enter password of length greater than 6"
                    view.New_pass.requestFocus()
                    return@setOnClickListener
                }
                if(updatedPass!=confirmPass) {
                    progressBar.visibility = View.INVISIBLE
                    view.confirm_new_pass.error = "Confirm Password is not same as New Password"
                    view.confirm_new_pass.requestFocus()
                    return@setOnClickListener
                }
                val credential =  EmailAuthProvider.getCredential(currentUser?.email!!,curPass)
                currentUser.reauthenticate(credential)
                    .addOnCompleteListener {task ->
                        when {
                            task.isSuccessful -> {
                                currentUser.updatePassword(updatedPass)
                                    .addOnCompleteListener{ task ->  
                                        if(task.isSuccessful){
                                            alert.dismiss()
                                            Toast.makeText(this, "Password Changed Successfully", Toast.LENGTH_SHORT ).show()
                                        }
                                    }
                            }
                            task.exception is FirebaseAuthInvalidCredentialsException -> {
                                progressBar.visibility = View.INVISIBLE
                                view.curr_pass.error = "Invalid Current Password"
                                view.curr_pass.requestFocus()
                            }

                        }
                    }
            }

        }

        profile_update.setOnClickListener {
            val newName = name.editText?.text.toString().trim()

            if(newName!= sName){
                val map = mutableMapOf<String, Any?>()
                map["Name"] = newName
                FirebaseDatabase.getInstance().getReference("Users/$userId")
                    .setValue(map)
                    .addOnSuccessListener {
                        pFlag = true
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error! Try again.", Toast.LENGTH_SHORT ).show()
                    }
            }
            if(flag){
                val storage:FirebaseStorage = FirebaseStorage.getInstance()
                val uploader: StorageReference = storage.getReference("Image1"+ Random(50))
                uploader.putFile(filePath)
                    .addOnSuccessListener {
                        uploader.downloadUrl
                            .addOnSuccessListener{
                                val map = mutableMapOf<String, Any?>()
                                map["Image"] = it.toString()
                                FirebaseDatabase.getInstance().getReference("Users/$userId")
                                    .setValue(map)
                                    .addOnSuccessListener {
                                        pFlag = true
                                    }
                                    .addOnFailureListener{
                                        Toast.makeText(this, "Error! Try again.", Toast.LENGTH_SHORT ).show()
                                    }
                            }
                    }

            }
            if(pFlag || flag){
                Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT ).show()
            }else{
                Toast.makeText(this, "Nothing to Update", Toast.LENGTH_SHORT ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            CropImage.activity(uri)
                .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                filePath = result.uri
                try {
                    val inputStream = filePath.let { contentResolver.openInputStream(it) }
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    flag = true
                    profile_image.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent,"Please select Image"),
            1
        )
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        p1?.continuePermissionRequest()
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        TODO("Not yet implemented")
    }


}


