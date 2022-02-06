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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment

@Suppress("DEPRECATION", "UNUSED_LAMBDA_EXPRESSION")

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

    lateinit var activity : MainActivity

    private val OPEN_GALLERY = 200

    val items = mutableListOf<ListViewItem>()
    val adapter = ListViewAdapter(items)

    companion object {
        var index:Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        image_dbManager = AddFragment.ImageDBManager(requireActivity(), "ImageTBL", null, 1)
        image_sqlDB = image_dbManager.writableDatabase

        text_dbManager =AddFragment.TextDBManager(requireActivity(), "textTBL", null, 1)
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

        // 이미지 올리기
        btnSelect.setOnClickListener { openGallery() }

        // 올리기
        // (이미지: 필수 입력 / 제목,내용: 미입력 시 기본 문구 들어감)
        btnUp.setOnClickListener {
            text_sqlDB = text_dbManager.writableDatabase

            // 이미지X
            if ( listview.isEmpty()
            ) {
                Toast.makeText(getActivity(), "이미지는 필수 입력 사항입니다!", Toast.LENGTH_SHORT).show()
            }
            // 제목O 내용X 이미지O
            else if(edtTitle.text.toString().isNotBlank() && edtContents.text.toString().isBlank() && listview.isNotEmpty()) {
                text_sqlDB.execSQL(
                    "INSERT INTO textTBL VALUES ( '"
                            + index + "' , '"
                            + edtTitle.text.toString() + "' , '"
                            + "no content" + "');"
                )
                Toast.makeText(getActivity(), "이미지, 제목 업로드 완료 ", Toast.LENGTH_SHORT).show()
                items.clear()
                adapter.notifyDataSetChanged()
                listview.adapter = adapter // 이미지 초기화
                edtTitle.text = null // 제목 초기화
            }
            // 제목X 내용O 이미지O
            else if(edtTitle.text.toString().isBlank() && edtContents.text.toString().isNotBlank() && listview.isNotEmpty()) {
                text_sqlDB.execSQL(
                    "INSERT INTO textTBL VALUES ( '"
                            + index + "' , '"
                            + "no title" + "' , '"
                            + edtContents.text.toString() + "');"
                )
                Toast.makeText(getActivity(), "이미지, 내용 업로드 완료 ", Toast.LENGTH_SHORT).show()
                items.clear()
                adapter.notifyDataSetChanged()
                listview.adapter = adapter // 이미지 초기화
                edtContents.text = null // 내용 초기화
            }
            // 제목X 내용X 이미지O
            else if(edtTitle.text.toString().isBlank() && edtContents.text.toString().isBlank() && listview.isNotEmpty()) {
                text_sqlDB.execSQL(
                    "INSERT INTO textTBL VALUES ( '"
                            + index + "' , '"
                            + "no title" + "' , '"
                            + "no content" + "');"
                )
                Toast.makeText(getActivity(), "이미지 업로드 완료 ", Toast.LENGTH_SHORT).show()
                items.clear()
                adapter.notifyDataSetChanged()
                listview.adapter = adapter // 이미지 초기화
            }
            // 제목O 내용O 이미지O
            else{
                // 데베 저장
                text_sqlDB.execSQL(
                    "INSERT INTO textTBL VALUES ( '"
                            + index + "' , '"
                            + edtTitle.text.toString() + "' , '"
                            + edtContents.text.toString() + "');"
                )
                Toast.makeText(getActivity(), "업로드 완료 ", Toast.LENGTH_SHORT).show()

                items.clear()
                adapter.notifyDataSetChanged()
                listview.adapter = adapter
                edtTitle.text = null
                edtContents.text = null
            }
            index += 1  // 인덱스 증가
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

        if(resultCode == Activity.RESULT_OK ) {
            if(requestCode == OPEN_GALLERY) {

                var clipData: ClipData = data!!.clipData!!

                for(j in 0 until clipData.itemCount) {

                    var currentImageUrl : Uri? = clipData.getItemAt(j).uri

                    // 이미지 데베 저장
                    image_sqlDB.execSQL(
                        "INSERT INTO ImageTBL VALUES ( '"
                                + currentImageUrl.toString() + "' , '"
                                + index + "');"
                    )

                    // 리스트뷰에 이미지 띄우기(비트맵 활용)
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity?.contentResolver,
                            currentImageUrl
                        )
                        items.add(ListViewItem(bitmap))

                        listview.adapter = adapter

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    // 게시글 이미지 저장 db
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

    // 게시글 제목, 내용 저장 db
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