@file:Suppress("DEPRECATION")

package com.example.studentapp.Resources

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.studentapp.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class UploadViewNotes : AppCompatActivity() {


    val editPDF : EditText = findViewById(R.id.pdf_name)
    val btn_upload : Button = findViewById(R.id.btnUpload)

    val storage_ref : StorageReference = FirebaseStorage.getInstance().getReference()
    val database_ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Notes")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_view_notes)


        btn_upload.setOnClickListener{

            val intent = Intent()
            intent.type = "application/pdf"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"Select PDF file"), 1 )



        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && requestCode == RESULT_OK && data!= null && data!!.getData()!= null ) {
            val fil: Uri = data!!.getData()!!
            uploadPDFNotes(fil)
        }




    }

    private fun uploadPDFNotes(data: Uri) {

        val progressd : ProgressDialog = ProgressDialog(this)
        progressd.setTitle("Uploading..")
        progressd.show()
        val ref = storage_ref.child("Notes/"+System.currentTimeMillis() + ".pdf")
        ref.putFile(data).addOnSuccessListener {
            object : OnSuccessListener<UploadTask.TaskSnapshot> {
                override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot?) {
                    val uri : Task<Uri>? = taskSnapshot?.storage?.downloadUrl
                    while(uri?.isComplete == false) {}
                    val url : Uri? = uri?.getResult()
                    val uploadPDF : ClassNotes = ClassNotes(editPDF.text.toString(),url.toString())
                    database_ref.push().key?.let { it1 -> database_ref.child(it1).setValue(uploadPDF) }
                    Toast.makeText(this@UploadViewNotes, "File Uploaded", Toast.LENGTH_SHORT).show()

                    progressd.dismiss()
                }

            }
        }.addOnProgressListener {
            object : OnProgressListener<UploadTask.TaskSnapshot>{
                override fun onProgress(snapshot: UploadTask.TaskSnapshot) {
                    var progress: Double = (100.0 * snapshot.bytesTransferred)/snapshot.totalByteCount
                    progressd.setMessage("Uploaded " + progress + "%")

                }

            }
        }

    }

}


