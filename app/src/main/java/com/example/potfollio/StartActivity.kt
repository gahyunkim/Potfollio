package com.example.potfollio

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity(){
    lateinit var btnStart : Button
    lateinit var image_sqlDB: SQLiteDatabase
    lateinit var text_sqlDB: SQLiteDatabase
    lateinit var image_dbManager: AddFragment.ImageDBManager
    lateinit var text_dbManager: AddFragment.TextDBManager


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

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

        btnStart = findViewById(R.id.btnStart)

        btnStart.setOnClickListener{
            // 로그인화면으로 전환
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

}