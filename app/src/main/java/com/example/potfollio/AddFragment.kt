package com.example.potfollio

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.potfollio.databinding.ActivityMainBinding
import java.io.File
import java.lang.Exception

@Suppress("DEPRECATION") //이거 추가하니까 갤러리 접근 가능
// 갤러리 접근 주의하는 문구 띄우는거 수정하기!!!!!!!!!!!!!!

class AddFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var btnSelect : Button
    lateinit var imgView1 : ImageView
    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: AddFragment.DBManager

    private val OPEN_GALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbManager = AddFragment.DBManager(activity!!, "groupTBL", null, 1)
        sqlDB = dbManager.writableDatabase
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

                var currentImageUrl : Uri? = data?.data

                sqlDB.execSQL( //
                    "INSERT INTO imageTBL VALUES ( '"
                            + currentImageUrl.toString() + "');"
                )
                sqlDB.close()
                // 이미지 저장 완료 문구
                Toast.makeText(activity, "이미지 저장 완료.", Toast.LENGTH_SHORT).show()

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

    // Image의 절대경로 가져오는 메소드
//    private fun getRealPathFromURI(contentUri: Uri?): String? {
//        if (contentUri?.path!!.startsWith("/storage")) {
//            return contentUri.path!!
//        }
//        var id :String = DocumentsContract.getDocumentId(contentUri).split(":")[1]
//        var columns: arrayOf<String> = { MediaStore.Files.FileColumns.DATA }
//        var selection:String = MediaStore.Files.FileColumns._ID + " = " + id;
//        val cursor : Cursor? = activity?.getContentResolver()?.query(MediaStore.Files.getContentUri("external"),
//            columns, selection, null, null)
//        try {
//            var columnIndex: Int = cursor!!.getColumnIndex(columns[0])
//            if (cursor!!.moveToFirst()) { return cursor.getString(columnIndex)
//            }
//        } finally {
//            cursor!!.close()
//        }; return null
//    }


    class DBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE imageTBL ( image URI );")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {
        }
    }
    override fun onClick(p0: View?) {
    }
}