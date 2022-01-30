package com.example.potfollio

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
// 지워도 되는 액티비티..!

class AddActivity : AppCompatActivity(){
//    lateinit var btnSelect : Button
//    lateinit var imgView1 : ImageView
//
//    private val OPEN_GALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add)

//        btnSelect = findViewById(R.id.btnSelect)
//        imgView1 = findViewById(R.id.imgView1)
//
//        btnSelect.setOnClickListener{ openGallery() }
    }

//    private fun openGallery(){
//        // 갤러리 접근
//        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.setType("image/*")
//        startActivityForResult(intent, OPEN_GALLERY)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(resultCode == Activity.RESULT_OK) {
//            if(requestCode == OPEN_GALLERY) {
//
//                var currentImageUrl : Uri?= data?.data
//
//                try{
//                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
//                    imgView1.setImageBitmap(bitmap)
//                }catch(e:Exception){
//                    e.printStackTrace()
//                }
//            }
//        } else{
//            Log.d("ActivityResult","something wrong")
//        }
//    }

}