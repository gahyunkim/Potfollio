package com.example.potfollio

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class StartActivity : AppCompatActivity(){
    lateinit var btnStart : Button
    lateinit var image_sqlDB: SQLiteDatabase
    lateinit var text_sqlDB: SQLiteDatabase
    lateinit var pro_sqlDB : SQLiteDatabase
    lateinit var image_dbManager: AddFragment.ImageDBManager
    lateinit var text_dbManager: AddFragment.TextDBManager
    lateinit var pro_dbManager : ChangeFragment.ProDBManager

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        //gif부분
        val imageView : ImageView = findViewById(R.id.logo_start)
        Glide.with(this).load(R.raw.logo_animation_start).override(500,500).fitCenter().into(imageView)

        // 시작 시 게시글 DB 초기화
        image_dbManager = AddFragment.ImageDBManager(this, "ImageTBL", null, 1)
        image_sqlDB = image_dbManager.writableDatabase
        image_sqlDB.execSQL("DELETE FROM ImageTBL")
        image_sqlDB.close()
        image_dbManager.close()
        text_dbManager = AddFragment.TextDBManager(this, "textTBL", null, 1)
        text_sqlDB = text_dbManager.writableDatabase
        text_sqlDB.execSQL("DELETE FROM textTBL")
        text_sqlDB.close()
        text_dbManager.close()

        // 시작 시 프로필 사진 초기화
        pro_dbManager = ChangeFragment.ProDBManager(this, "ProTBL", null, 1)
        pro_sqlDB = pro_dbManager.writableDatabase
        pro_sqlDB.execSQL("DELETE FROM ProTBL")
        pro_sqlDB.close()
        pro_dbManager.close()

        btnStart = findViewById(R.id.btnStart)

        btnStart.setOnClickListener{
            // 로그인화면으로 전환
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}