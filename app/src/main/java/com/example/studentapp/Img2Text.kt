package com.example.studentapp

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.theartofdev.edmodo.cropper.CropImage
import java.io.IOException

class Img2Text : AppCompatActivity(), PermissionListener {
    var text: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img2_text)

        val convertedText: EditText = findViewById(R.id.converted_text)
        val takePicture: Button = findViewById(R.id.take_picture)
        val convert: Button = findViewById(R.id.convert)

        convertedText.visibility = View.INVISIBLE

        takePicture.setOnClickListener {
            Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(this)
                .check()
        }

        convert.setOnClickListener {
            if(text!=""){
                takePicture.visibility = View.INVISIBLE
                convert.visibility = View.INVISIBLE
                convertedText.visibility = View.VISIBLE

                convertedText.setText(text)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            CropImage.activity(uri)
                .start(this)
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {
                val imageToConvert: ImageView = findViewById(R.id.image_to_convert)
                try {
                    val inputStream = result.uri.let { contentResolver.openInputStream(it) }
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageToConvert.setImageBitmap(bitmap)

                    val image: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap)
                    val textRecognizer: FirebaseVisionTextRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer

                    val task: Task<FirebaseVisionText> = textRecognizer.processImage(image)

                    task.addOnSuccessListener {
                        text = it.text

                    }

                    task.addOnFailureListener {
                        Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                    }

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


