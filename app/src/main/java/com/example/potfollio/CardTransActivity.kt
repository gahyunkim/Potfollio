package com.example.potfollio

import android.content.Intent
import android.content.pm.ActivityInfo
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class CardTransActivity : AppCompatActivity() {
    lateinit var card_dbManager : ChangeFragment.DBManager
    lateinit var card_sqlDB : SQLiteDatabase
    lateinit var cname : TextView
    lateinit var cnickname : TextView
    lateinit var csns : TextView
    lateinit var cphone : TextView
    lateinit var cmail : TextView
    lateinit var cinfo : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_trans)

        // 화면을 가로로 고정(landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        cname = findViewById(R.id.cname)
        cnickname = findViewById(R.id.cnickname)
        cinfo = findViewById(R.id.cinfo)
        csns = findViewById(R.id.csns)
        cphone = findViewById(R.id.cphone)
        cmail = findViewById(R.id.cmail)

        card_dbManager = ChangeFragment.DBManager(this, "CardTBL", null, 1)
        card_sqlDB = card_dbManager.writableDatabase

        var c_name = intent.getStringExtra("cname")

        var card_cursor: Cursor
        card_cursor = card_sqlDB.rawQuery(
            "SELECT Name,NickName,Info,Sns,Phone,Mail FROM CardTBL WHERE Name='" + c_name + "'",
            null
        )

        if(card_cursor.count==0){
            // 동일한 이름을을 가지는 테이이 없는 경우 새롭게 테이블을 생성함 + 기본 데이터를 넣어줌
            cname.text = c_name?.toUpperCase()
            cnickname.text =  "Game Director"
            cinfo.text =  "안녕하세요.\n저의 Pot, Folio에 방문해주셔서 감사합니다."
            csns.text =  "pp734.k"
            cphone.text =  "010.6345.6284"
            cmail.text =  "ikeyun3301@gmail.com"
        }
        else if(card_cursor.count==1){
            // 동일한 이름을 가지는 테이블이 있는 경우 데이터베이스로 부터 각각의 데이터를 얻어서 명함 페이지 구성
            card_cursor.moveToNext()
            cname.text = card_cursor.getString(0).toUpperCase()
            cnickname.text =  card_cursor.getString(1)
            cinfo.text =  card_cursor.getString(2)
            csns.text =  card_cursor.getString(3)
            cphone.text =  card_cursor.getString(4)
            cmail.text =  card_cursor.getString(5)
        }
    }
}