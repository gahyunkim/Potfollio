package com.example.potfollio

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_post.*

@Suppress("DEPRECATION")

class PostActivity : AppCompatActivity() {
    lateinit var post_back: Button
    lateinit var infor_sqlDB: SQLiteDatabase
    lateinit var image_sqlDB: SQLiteDatabase
    lateinit var text_sqlDB: SQLiteDatabase
    lateinit var image_dbManager: AddFragment.ImageDBManager
    lateinit var text_dbManager: AddFragment.TextDBManager
    lateinit var infor_dbManager: SignUpActivity.DBManager

    lateinit var recyclerview: RecyclerView
    lateinit var name: TextView
    lateinit var topic: TextView
    lateinit var contents: TextView

    var postId : Int =0

    val items = ArrayList<RecyclerViewItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //post_back = findViewById(R.id.post_back)
        recyclerview = findViewById(R.id.recyclerview)
        topic = findViewById(R.id.topic)
        contents = findViewById(R.id.name)
        name = findViewById(R.id.name)

        postId = intent.getIntExtra("postId", 100)

        infor_dbManager = SignUpActivity.DBManager(this, "groupTBL", null, 1)
        infor_sqlDB = infor_dbManager.readableDatabase

        image_dbManager = AddFragment.ImageDBManager(this, "ImageTBL", null, 1)
        image_sqlDB = image_dbManager.readableDatabase

        text_dbManager = AddFragment.TextDBManager(this, "textTBL", null, 1)
        text_sqlDB = text_dbManager.readableDatabase

        var cursor_infor: Cursor
        cursor_infor = infor_sqlDB.rawQuery("SELECT gID, gPass,gName FROM groupTBL", null)

        var cursor_image: Cursor
        cursor_image = image_sqlDB.rawQuery("SELECT image , i_number FROM ImageTBL", null)

        var cursor_text: Cursor
        cursor_text = text_sqlDB.rawQuery("SELECT i_number , title , contents FROM textTBL", null)

        // postId랑 같은 데베 i_number 찾아서 게시물에 뿌리기

        // 이미지 보이기
        while (cursor_image.moveToNext()) {
            var iImage = cursor_image.getString(0)
            var iNum = cursor_image.getInt(1)

            if(postId == iNum) {
                var currentImageUri : Uri = iImage.toUri()

                // 리사이클러뷰에 이미지 띄우기(비트맵 활용)
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        currentImageUri
                    )

                    val adapter = RecyclerViewAdapter(items)
                    recyclerview.adapter = adapter

                    // 레이아웃 매니저
                    val llm = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false) //좌우 스크롤
                    recyclerview.layoutManager = llm

                    //recyclerview.setHasFixedSize(true)

                    items.add(RecyclerViewItem(bitmap))

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        // 제목, 내용 보이기
        while (cursor_text.moveToNext()) {
            var iNum = cursor_text.getInt(0)
            var iTitle = cursor_text.getString(1)
            var iContent = cursor_text.getString(2)

            if(postId == iNum) {
                topic.text = iTitle
                contents.text = iContent
            }
        }

        // 사용자 이름 가져오기 (계정 여러개일 경우 조건 추가하기)
//        while (cursor_infor.moveToNext()) {
//            var iName = cursor_infor.getString(2)
//
//            name.text = iName
//        }

//        post_back.setOnClickListener {
//            // 액티비티가 바로 종료되도록 함
//            finish()
//        }
    }

}
