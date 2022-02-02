package com.example.potfollio

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment

@Suppress("DEPRECATION", "UNUSED_LAMBDA_EXPRESSION") //이거 추가하니까 갤러리 접근 가능
// 갤러리 접근 주의하는 문구 띄우기
// 갤러리 다시 접근하기

class AddFragment : Fragment(), View.OnClickListener {
    lateinit var btnSelect : Button
    lateinit var image_sqlDB: SQLiteDatabase
    lateinit var text_sqlDB: SQLiteDatabase
    lateinit var image_dbManager: AddFragment.ImageDBManager
    lateinit var text_dbManager: AddFragment.TextDBManager
    lateinit var listview : ListView
    lateinit var edtTitle : EditText
    lateinit var edtContents : EditText
    lateinit var btnUp : Button

    private val OPEN_GALLERY = 200

    val items = mutableListOf<ListViewItem>()

    // 리스트에 주소 저장해 놓고 '올리기' 누르면 데베에 올리기
    // var urilist = ArrayList<Uri>()

    companion object {
        var index:Int = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        image_dbManager = AddFragment.ImageDBManager(activity!!, "ImageTBL", null, 1)
        image_sqlDB = image_dbManager.writableDatabase
        //image_sqlDB.execSQL("DROP TABLE imageTBL")
        //image_sqlDB.close()
        //image_dbManager.close()

        text_dbManager =AddFragment.TextDBManager(activity!!, "textTBL", null, 1)
        text_sqlDB = text_dbManager.writableDatabase
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {
        val view: View = inflater.inflate(R.layout.fragment_add, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSelect = view.findViewById(R.id.btnSelect)
        listview = view.findViewById(R.id.listview)
        edtTitle = view.findViewById(R.id.edtTitle)
        edtContents = view.findViewById(R.id.edtContents)
        btnUp = view.findViewById(R.id.btnUp)

        btnSelect.setOnClickListener { openGallery() }

        // 올리기 실행 시
        btnUp.setOnClickListener {
            text_sqlDB = text_dbManager.writableDatabase

            if (edtTitle.text.toString().isBlank() || edtContents.text.toString()
                    .isBlank() || listview.isEmpty()
            ) {
                // 제목,내용,이미지는 필수 입력 사항
                Toast.makeText(getActivity(), "제목,이미지,내용을 모두 작성해 주세요!", Toast.LENGTH_SHORT).show()
            }
            else {
                // 데베 저장
                text_sqlDB.execSQL(
                    "INSERT INTO textTBL VALUES ( '"
                            + index + "' , '"
                            + edtTitle.text.toString() + "' , '"
                            + edtContents.text.toString() + "');"
                )
                //text_sqlDB.close()

            }
            index += 1  // 인덱스 증가

            // 마이페이지프래그먼트로 바로 돌아가도록 할까...?
            Toast.makeText(getActivity(), "올리기 완료 \n 마이페이지에서 확인하세요", Toast.LENGTH_SHORT).show()
        }
    }

    // 갤러리 접근
    private fun openGallery(){
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, OPEN_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == OPEN_GALLERY) {

                var clipData: ClipData = data!!.clipData!!

                for(j in 0..clipData.itemCount-1) {

                    var currentImageUrl : Uri? = clipData.getItemAt(j).uri

                    // 데베 저장
                    image_sqlDB.execSQL(
                        "INSERT INTO ImageTBL VALUES ( '"
                                + currentImageUrl.toString() + "' , '"
                                + index + "');"
                    )
                    //image_sqlDB.close()

                    // 리스트뷰에 이미지 띄우기(비트맵 활용)
                    try {
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
            }
        } else{
            Log.d("ActivityResult", "something wrong")
        }
    }

    class ImageDBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE ImageTBL ( image TEXT NOT NULL, i_number INTEGER NOT NULL);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {
        }
    }

    class TextDBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE textTBL ( i_number INTEGER , title STRING , contents STRING);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {
        }
    }

    override fun onClick(p0: View?) {
    }
}