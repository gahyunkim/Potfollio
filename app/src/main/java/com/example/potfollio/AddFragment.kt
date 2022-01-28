package com.example.potfollio

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.potfollio.databinding.ActivityMainBinding
import java.lang.Exception

@Suppress("DEPRECATION") //이거 추가하니까 갤러리 접근 가능!
// 갤러리 접근 주의하는 문구 띄우는거 수정하기!!!!!!!!!!!!!! 잊지말쟈 문선민

class AddFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var btnSelect : Button
    lateinit var imgView1 : ImageView

    private val OPEN_GALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {

        val view: View = inflater!!.inflate(R.layout.fragment_add,container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSelect = view.findViewById(R.id.btnSelect)
        imgView1 = view.findViewById(R.id.imgView1)

        btnSelect.setOnClickListener{ openGallery() }
    }

    private fun openGallery(){
        // 갤러리 접근
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == OPEN_GALLERY) {

                var currentImageUrl : Uri?= data?.data

                try{
                    val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, currentImageUrl)
                    imgView1.setImageBitmap(bitmap)
                }catch(e: Exception){
                    e.printStackTrace()
                }
            }
        } else{
            Log.d("ActivityResult","something wrong")
        }
    }

    override fun onClick(p0: View?) {
    }

}