package com.example.potfollio

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.potfollio.databinding.ActivityMainBinding

@Suppress("DEPRECATION", "UNUSED_LAMBDA_EXPRESSION") //이거 추가하니까 갤러리 접근 가능
// 갤러리 접근 주의하는 문구 띄우는거 수정하기!!!!!!!!!!!!!!

class AddFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var btnSelect : Button
    lateinit var imgView1 : ImageView
    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: AddFragment.DBManager
    lateinit var listview : ListView

    private val OPEN_GALLERY = 200

     val items = mutableListOf<ListViewItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbManager = AddFragment.DBManager(activity!!, "imageTBL", null, 1)
        sqlDB = dbManager.writableDatabase


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {
        val view: View = inflater!!.inflate(R.layout.fragment_add, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSelect = view.findViewById(R.id.btnSelect)
            //imgView1 = view.findViewById(R.id.imgView1)
        listview = view.findViewById(R.id.listview)
        btnSelect.setOnClickListener{ openGallery() }
    }

    private fun openGallery(){
        // 갤러리 접근
        val intent: Intent = Intent(Intent.ACTION_PICK)
        //intent.setType("image/*")
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        //intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, OPEN_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == OPEN_GALLERY) {

//                            if (data!!.clipData == null) {
//                            }
//                            else {
//                                 var clipData: ClipData = data.clipData!!;
//                                if (clipData.itemCount > 3) { //사진은 3장을 초과해서는 안된다.
//                                   // Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT).show()
//                                }
//                                else if (clipData.itemCount == 1) { //사진을 1개 선택했을 때
//                                    var img_path: Uri = clipData.getItemAt(0).uri; //이미지 URI
//                                    var pictures: ListView = view!!.findViewById(R.id.listview)
//                                    pictures.add(img_path);  //사진 리스트에 추가
//                                }
//                                else if (clipData.getItemCount() < 3 && clipData.getItemCount() > 1) { //사진을 1개 이상 선택했을 때
//                                    for (int i = 0; i < clipData.getItemCount(); i++) {
//                                        Uri img_path = clipData.getItemAt(i).getUri();
//                                        adapter_pictures.addData(img_path);
//                                    }
//                                }
//                            }

//                var currentImageUrl : Uri? = data?.data


                /* 데베 저장하는 부분 잠시 보류 !!! 지우면 안돼!!!
                sqlDB.execSQL( //
                    "INSERT INTO imageTBL VALUES ( '"
                            + currentImageUrl.toString() + "');"
                )
                sqlDB.close()
                */

//                // 이미지 저장 완료 문구
//                Toast.makeText(activity, "이미지 저장 완료.", Toast.LENGTH_SHORT).show()

                var clipData: ClipData = data!!.clipData!!

                for(j in 0..clipData.itemCount-1) {

                    // 리스트에가다 주소 저장해 놓고 데베에 올리기..

                     var currentImageUrl : Uri? = clipData.getItemAt(j).uri

                    try { // 이미지뷰에 이미지 띄우기(비트맵 활용)
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity?.contentResolver,
                            currentImageUrl
                        )

                        items.add(ListViewItem(bitmap))

                        val adapter = ListViewAdapter(items)
                        listview.adapter = adapter

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                // 이미지 저장 완료 문구
                Toast.makeText(activity, "이미지 선택 완료.", Toast.LENGTH_SHORT).show()
            }
        } else{
            Log.d("ActivityResult", "something wrong")
        }
    }

    class DBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE imageTBL ( image STRING );")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {
        }
    }
    override fun onClick(p0: View?) {
    }
}