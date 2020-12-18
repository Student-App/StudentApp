package com.example.studentapp.uploads

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class AddFile : AppCompatActivity(), PermissionListener {
    private lateinit var filePath: Uri



    val text = intent.getStringExtra("ID")
    val dept = intent.getStringExtra("dept")

    val uploader: DatabaseReference = FirebaseDatabase.getInstance().getReference("Uploads/$dept/Courses/$text/files")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_file)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val imageBrowse: ImageView = findViewById(R.id.image_browse)
        val imageUpload: ImageView = findViewById(R.id.imageUpload)
        val cancelFile: ImageView = findViewById(R.id.cancel_file)

        val upload: Button = findViewById(R.id.upload_file)

        imageUpload.visibility = View.INVISIBLE
        cancelFile.visibility = View.INVISIBLE

        cancelFile.setOnClickListener{
            imageUpload.visibility = View.INVISIBLE
            cancelFile.visibility = View.INVISIBLE
            imageBrowse.visibility = View.VISIBLE
        }

        imageBrowse.setOnClickListener {
            Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(this)
                .check()
        }

        upload.setOnClickListener {
            processUpload()
        }


    }

    private fun processUpload() {

        lateinit var progressDialog: ProgressDialog
        progressDialog.setTitle("File Uploading.....!!!")
        progressDialog.show()


        val refrence: StorageReference = FirebaseStorage.getInstance().getReference("Resources/"+System.currentTimeMillis()+".pdf")
        refrence.putFile(filePath)
            .addOnSuccessListener {
                refrence.downloadUrl
                    .addOnSuccessListener{
                        val fileTitle: TextInputLayout = findViewById(R.id.file_name)
                        val imageBrowse: ImageView = findViewById(R.id.image_browse)
                        val imageUpload: ImageView = findViewById(R.id.imageUpload)
                        val cancelFile: ImageView = findViewById(R.id.cancel_file)
                        val map = mutableMapOf<String, Any?>()
                        map["file_name"] = fileTitle.editText.toString()
                        map["file_url"] = it.toString()
                        uploader.push().key?.let { it1 ->
                            uploader.child(it1).setValue(map)
                                .addOnSuccessListener {
                                    imageUpload.visibility = View.INVISIBLE
                                    cancelFile.visibility = View.INVISIBLE
                                    imageBrowse.visibility = View.VISIBLE
                                    fileTitle.editText?.setText("")
                                    progressDialog.dismiss()
                                    Toast.makeText(this, "File uploaded Successfully", Toast.LENGTH_SHORT ).show()
                                }
                                .addOnFailureListener{
                                    Toast.makeText(this, "Error! Try again.", Toast.LENGTH_SHORT ).show()
                                }
                        }
                    }

            }
            .addOnProgressListener {
                var percent = (100*it.bytesTransferred/it.totalByteCount)
                progressDialog.setMessage("Uploaded"+ percent.toInt() +"%")
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val imageBrowse: ImageView = findViewById(R.id.image_browse)
            val imageUpload: ImageView = findViewById(R.id.imageUpload)
            val cancelFile: ImageView = findViewById(R.id.cancel_file)
            filePath = data.data!!
            imageUpload.visibility = View.VISIBLE
            cancelFile.visibility = View.VISIBLE
            imageBrowse.visibility = View.INVISIBLE
        }

    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(
            Intent.createChooser(intent,"Please select PDF File"),
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